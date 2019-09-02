package cat.calidos.jsltui.control.injection;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Module
public class JSLTPreviewControlModule {

protected final static Logger log = LoggerFactory.getLogger(JSLTPreviewControlModule.class);

private static final String APPLY_PATH = "/preview/?";

public static final String JSLT_PARAM = "jslt";
public static final String JSON_PARAM = "json";

@Provides @IntoMap @Named("GET")
@StringKey(APPLY_PATH)
public static BiFunction<List<String>, Map<String, String>, String> apply() {

	return (pathElems, params) -> {

		String transformation = JSLTApplierControlModule.apply().apply(pathElems, params);
		
		String out;
		return "";

	};
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

