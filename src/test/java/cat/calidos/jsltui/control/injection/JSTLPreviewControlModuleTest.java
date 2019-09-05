package cat.calidos.jsltui.control.injection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.calidos.morfeu.utils.MorfeuUtils;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class JSTLPreviewControlModuleTest {

private ArrayList<String> pathElems;
private ObjectMapper mapper;


@BeforeEach
public void setup() {

	pathElems = new ArrayList<String>(0);
	pathElems.add("preview");
	mapper = JSLTPreviewControlModule.jsonMapper();

}


@Test @DisplayName("Number of params error")
public void applierParamsTest() throws Exception {

	String result = JSLTPreviewControlModule.preview(mapper).apply(pathElems, MorfeuUtils.paramStringMap());
	assertAll("empty parameters tests",
		() -> assertNotNull(result),
		() -> assertTrue(result.contains("alert"), "No problem field in error JSON"),
		() -> assertTrue(result.contains("Wrong number of parameters"), "Did not find expected problem message")
	);

}

@Test @DisplayName("Identity parsing")
public void applierTest() throws Exception {

	String json = "{\"foo\":\"bar\"}";
	Map<String, String> params = MorfeuUtils.paramStringMap(JSLTApplierControlModule.JSLT_PARAM, ".",
															JSLTApplierControlModule.JSON_PARAM, json);
	String result = JSLTPreviewControlModule.preview(mapper).apply(pathElems, params);
	assertAll("empty parameters tests",
		() -> assertNotNull(result),
		() -> assertTrue(result.contains("<code>"), "Does not show code"),
		() -> assertTrue(result.contains("\"foo\" : \"bar\""), "Does not include output")
	);

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

