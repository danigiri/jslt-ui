package cat.calidos.jsltui.control.injection;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import cat.calidos.morfeu.utils.MorfeuUtils;
import cat.calidos.morfeu.utils.injection.DaggerJSONParserComponent;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class JSLTApplierControlModuleTest {

private ArrayList<String> pathElems;


@Before
public void setup() {

	pathElems = new ArrayList<String>(0);
	pathElems.add("apply");
	
}


@Test @DisplayName("Number of params error")
public void applierIdentityTest() throws Exception {

	String result = JSLTApplierControlModule.apply().apply(pathElems, MorfeuUtils.paramStringMap());
	JsonNode outputNode = DaggerJSONParserComponent.builder().from(result).build().json().get();
	assertAll("empty parameters tests",
			() -> assertNotNull(outputNode),
			() -> assertTrue(outputNode.has("__problem"), "No problem field in error JSON"),
			() -> assertEquals("Wrong number of parameters", outputNode.get("__problem").asText())
			);

}


@Test @DisplayName("Identity parsing")
public void applierTest() throws Exception {

	String json = "{\"foo\":\"bar\"}";
	Map<String, String> params = MorfeuUtils.paramStringMap(JSLTApplierControlModule.JSLT_PARAM, ".",
															JSLTApplierControlModule.JSON_PARAM, json);
	String result = JSLTApplierControlModule.apply().apply(pathElems, params);
	JsonNode outputNode = DaggerJSONParserComponent.builder().from(result).build().json().get();
	JsonNode expected = DaggerJSONParserComponent.builder().from(json).build().json().get();
	assertAll("empty parameters tests",
			() -> assertNotNull(outputNode),
			() -> assertEquals(expected, outputNode)
			);

}


@Test @DisplayName("Incorrect JSLT")
public void incorrectJSTLTest() throws Exception {

	String json = "{\"foo\":\"bar\"}";
	Map<String, String> params = MorfeuUtils.paramStringMap(JSLTApplierControlModule.JSLT_PARAM, "FOO",
															JSLTApplierControlModule.JSON_PARAM, json);
	String result = JSLTApplierControlModule.apply().apply(pathElems, params);
	JsonNode outputNode = DaggerJSONParserComponent.builder().from(result).build().json().get();
	String expectedProblem = "Problem when compiling JSLT expression";
	assertAll("incorrect tests",
			() -> assertNotNull(outputNode),
			() -> assertTrue(outputNode.get("__problem").asText().contains(expectedProblem))
			);

}


@Test @DisplayName("Incorrect input JSON")
public void incorrectJSONTest() throws Exception {

	String json = "{\"foo\":\"bar\"";
	Map<String, String> params = MorfeuUtils.paramStringMap(JSLTApplierControlModule.JSLT_PARAM, ".",
															JSLTApplierControlModule.JSON_PARAM, json);
	String result = JSLTApplierControlModule.apply().apply(pathElems, params);
	JsonNode outputNode = DaggerJSONParserComponent.builder().from(result).build().json().get();
	String expected = "Problem when parsing input JSON expression";
	assertAll("incorrect tests",
			() -> assertNotNull(outputNode),
			() -> assertTrue(outputNode.get("__problem").asText().contains(expected))
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

