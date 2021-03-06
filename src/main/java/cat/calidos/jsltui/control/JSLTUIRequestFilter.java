package cat.calidos.jsltui.control;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.calidos.jsltui.control.injection.DaggerJSLTUIServletFilter;
import cat.calidos.morfeu.control.MorfeuRequestFilter;
import cat.calidos.morfeu.webapp.injection.HttpFilterComponent;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class JSLTUIRequestFilter extends MorfeuRequestFilter {

protected final static Logger log = LoggerFactory.getLogger(JSLTUIRequestFilter.class);

@Override
protected HttpFilterComponent filterComponent(FilterChain chain, HttpServletRequest httpRequest,
												HttpServletResponse httpResponse) {
	return DaggerJSLTUIServletFilter.builder().request(httpRequest).response(httpResponse).chain(chain).build();
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

