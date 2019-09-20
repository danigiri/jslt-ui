package cat.calidos.jsltui.control;

import java.util.Map;

import cat.calidos.jsltui.control.injection.DaggerJSLTUIControlComponent;
import cat.calidos.jsltui.control.injection.JSLTUIControlComponent;
import cat.calidos.morfeu.control.MorfeuServlet;
import cat.calidos.morfeu.webapp.injection.ControlComponent;
import cat.calidos.morfeu.webapp.injection.DaggerControlComponent;


/**	This is a specialised MorfeuServlet as the code is a generic servlet that reads and configures
*	the RESOURCES_PREFIX configuration property. We reuse that logic and we override the get and put
*	methods with our specific JSLTUI controller logic.
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class JSLTUIServlet extends MorfeuServlet {


@Override
public ControlComponent getControl(String path, Map<String, String> params) {

	//FIXME: this s*cks a bit, need to move to its own controller code if it grows too much
	if (params.containsKey(JSLTUIControlComponent.URI_PARAM)) {
		String completeURI = resourcesPrefix+params.get(JSLTUIControlComponent.URI_PARAM);
		params.put(JSLTUIControlComponent.URI_PARAM, completeURI);
	}

	return DaggerJSLTUIControlComponent.builder()
										.withPath(path)
										.method(DaggerControlComponent.GET)
										.withParams(params)
										.andContext(context)
										.build();

}


@Override
public ControlComponent putControl(String path, Map<String, String> params) {
	return DaggerJSLTUIControlComponent.builder()
										.withPath(path)
										.method(DaggerControlComponent.POST)
										.withParams(params)
										.andContext(context)
										.build();
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

