package cat.calidos.jsltui.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import cat.calidos.jsltui.model.injection.JSLTApplierModule;
import cat.calidos.jsltui.problems.JSLTUIParserException;
import cat.calidos.morfeu.utils.injection.DaggerJSONParserComponent;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class JSLTApplierIntTest {


@Test @DisplayName("JSLT Applier parsing identity test")
public void applierIdentityTest() throws Exception {

	String jslt = ".";
	JSLTApplier applier = JSLTApplierModule.jsltApplier(jslt);
	assertNotNull(applier);

	String json = "{\"foo\":\"bar\"}";
	JsonNode node = DaggerJSONParserComponent.builder().from(json).build().json().get();
	assertEquals(node, applier.apply(node));

}


@Test @DisplayName("JSLT Applier exception test")
public void applierTestException() throws Exception {

	String jslt = "FOO";
	Assertions.assertThrows(JSLTUIParserException.class, () -> JSLTApplierModule.jsltApplier(jslt));

}


@Test @DisplayName("JSLT Applier parsing test")
public void applierTransformTest() throws Exception {

	String jslt = "{\"array\" : .foo.bar}";
	JSLTApplier applier = JSLTApplierModule.jsltApplier(jslt);
	assertNotNull(applier);

	String json = "{\"foo\" : {\"bar\" : [1,2,3,4,5]}}";
	JsonNode node = DaggerJSONParserComponent.builder().from(json).build().json().get();
	String expectedJSON = "{ \"array\" : [1,2,3,4,5] }";
	JsonNode expectedNode = DaggerJSONParserComponent.builder().from(expectedJSON).build().json().get();
	assertEquals(expectedNode, applier.apply(node));

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

