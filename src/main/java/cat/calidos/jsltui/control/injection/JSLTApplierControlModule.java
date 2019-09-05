// JSLT APPLIER CONTROL MODULE . JAVA

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

import cat.calidos.jsltui.model.injection.DaggerJSLTApplierComponent;
import cat.calidos.morfeu.utils.MorfeuUtils;
import cat.calidos.morfeu.view.injection.DaggerViewComponent;
import cat.calidos.morfeu.webapp.injection.ControlComponent;


/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Module
public class JSLTApplierControlModule {

protected final static Logger log = LoggerFactory.getLogger(JSLTApplierControlModule.class);

private static final String APPLY_PATH = "/apply/?";

public static final String JSLT_PARAM = "jslt";
public static final String JSON_PARAM = "json";


@Provides @IntoMap @Named("GET")
@StringKey(APPLY_PATH)
public static BiFunction<List<String>, Map<String, String>, String> apply() {

	return (pathElems, params) -> {

		String out;

		if (params.size()<2) {
			return DaggerViewComponent.builder()
										.withTemplatePath("templates/apply-problem.twig")
										.withValue(MorfeuUtils.paramMap())
										.andProblem("Wrong number of parameters")
										.build()
										.render();
		}

		try {

			String jslt = params.get(JSLT_PARAM);
			String content = params.get(JSON_PARAM);
			out = DaggerJSLTApplierComponent.builder().fromJSLT(jslt).andContent(content).build().apply().get();

		} catch (Exception e) {
			out = DaggerViewComponent.builder()
										.withTemplatePath("templates/apply-problem.twig")
										.withValue(MorfeuUtils.paramMap("stacktrace", e.toString()))
										.andProblem(e.getMessage())
										.build()
										.render();
		}

		return out;

	};

}


@Provides @IntoMap @Named("Content-Type")
@StringKey(APPLY_PATH)
public static String contentType() {
	return ControlComponent.JSON;
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

