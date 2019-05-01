package cat.calidos.jsltui.model.injection;

import dagger.producers.ProducerModule;
import dagger.producers.Produces;

import java.util.concurrent.ExecutionException;

import javax.inject.Named;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.spt.data.jslt.Expression;
import com.schibsted.spt.data.jslt.Parser;

import cat.calidos.jsltui.model.JSLTApplier;
import cat.calidos.jsltui.problems.JSLTUIParserException;
import cat.calidos.morfeu.problems.ParsingException;
import cat.calidos.morfeu.utils.injection.DaggerJSONParserComponent;
import cat.calidos.morfeu.utils.injection.MapperModule;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@ProducerModule(includes=MapperModule.class)
public class JSLTApplierModule {


@Produces 
public static String apply(JSLTApplier applier, JsonNode content, ObjectMapper mapper) throws JSLTUIParserException {

	JsonNode transformed = applier.apply(content);
	try {

		return mapper.writeValueAsString(transformed);

	} catch (JsonProcessingException e) {
		throw new JSLTUIParserException("Malformed JSON output from transformation", e);
	}

}


@Produces
public static JSLTApplier jsltApplier(@Named("JSLT") String jslt) throws JSLTUIParserException {

	try {

		Expression exp = Parser.compileString(jslt);

		return new JSLTApplier(exp);

	} catch (Exception e) {
		throw new JSLTUIParserException("Problem when compiling JSLT expression", e);
	}

}

@Produces
public static JsonNode content(@Named("Content") String json) throws JSLTUIParserException {
	try {
		return DaggerJSONParserComponent.builder().from(json).build().json().get();
	} catch (Exception e) {
		throw new JSLTUIParserException("Problem when parsing input JSON expression", e);
	}

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

