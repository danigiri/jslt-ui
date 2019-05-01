package cat.calidos.jsltui.model.injection;

import dagger.BindsInstance;
import dagger.producers.ProductionComponent;

import javax.inject.Named;

import com.google.common.util.concurrent.ListenableFuture;

import cat.calidos.jsltui.model.JSLTApplier;
import cat.calidos.jsltui.problems.JSLTUIParserException;
import cat.calidos.morfeu.utils.injection.ListeningExecutorServiceModule;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@ProductionComponent(modules={JSLTApplierModule.class, ListeningExecutorServiceModule.class})
public interface JSLTApplierComponent {


ListenableFuture<String> apply() throws JSLTUIParserException;


@ProductionComponent.Builder
interface Builder {

	@BindsInstance Builder fromJSLT(@Named("JSLT") String jslt);
	@BindsInstance Builder andContent(@Named("Content") String json);

	JSLTApplierComponent build();

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

