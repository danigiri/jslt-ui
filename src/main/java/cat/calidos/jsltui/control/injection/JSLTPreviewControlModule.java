package cat.calidos.jsltui.control.injection;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.calidos.jsltui.model.injection.DaggerJSLTApplierComponent;
import cat.calidos.morfeu.utils.MorfeuUtils;
import cat.calidos.morfeu.utils.injection.MapperModule;
import cat.calidos.morfeu.view.injection.DaggerViewComponent;


/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Module
public class JSLTPreviewControlModule {

protected final static Logger log = LoggerFactory.getLogger(JSLTPreviewControlModule.class);

private static final String APPLY_PATH = "/preview/?";


@Provides @IntoMap @Named("GET")
@StringKey(APPLY_PATH)
public static BiFunction<List<String>, Map<String, String>, String> preview(ObjectMapper mapper) {

	return (pathElems, params) -> {

		String out;

		if (params.size()<2) {
			return DaggerViewComponent.builder()
										.withTemplatePath("templates/preview-problem.twig.html")
										.withValue(MorfeuUtils.paramMap())
										.andProblem("Wrong number of parameters")
										.build()
										.render();
		}

		try {

			String jslt = params.get(JSLTApplierControlModule.JSLT_PARAM);
			String content = params.get(JSLTApplierControlModule.JSON_PARAM);
			out = DaggerJSLTApplierComponent.builder().fromJSLT(jslt).andContent(content).build().apply().get();
			Object json = mapper.readValue(out, Object.class);
			out = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			out = DaggerViewComponent.builder()
										.withTemplatePath("templates/preview.twig.html")
										.withValue(MorfeuUtils.paramMap("output", out))
										.build()
										.render();

		} catch (Exception e) {
			out = DaggerViewComponent.builder()
										.withTemplatePath("templates/preview-problem.twig.html")
										.withValue(MorfeuUtils.paramMap("stacktrace", e.toString()))
										.andProblem(e.getMessage())
										.build()
										.render();
		}

		return out;

	};

}


@Provides
public static ObjectMapper jsonMapper() {

	log.trace("[Producing ObjectMapper]");
	return new ObjectMapper();	//TODO: check if it is necessary to 'provide' default constructor objects in Dagger2

}

}

/*
 *    Copyright 2019 Daniel Giribet
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

