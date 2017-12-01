/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.netflix.titus.master.jobmanager.service.service.action;

import java.util.List;

import io.netflix.titus.api.jobmanager.model.job.ServiceJobTask;
import io.netflix.titus.api.jobmanager.service.V3JobOperations;
import io.netflix.titus.common.framework.reconciler.ModelActionHolder;
import io.netflix.titus.common.util.tuple.Pair;
import io.netflix.titus.master.jobmanager.service.common.action.JobChange;
import io.netflix.titus.master.jobmanager.service.common.action.TitusChangeAction;
import io.netflix.titus.master.jobmanager.service.common.action.TitusModelUpdateActions;
import rx.Observable;

public class RemoveServiceTaskAction extends TitusChangeAction {

    private static final String MESSAGE = "Removing finished task";

    public RemoveServiceTaskAction(ServiceJobTask finishedTask) {
        super(new JobChange(V3JobOperations.Trigger.Reconciler, finishedTask.getId(), MESSAGE));
    }

    @Override
    public Observable<Pair<JobChange, List<ModelActionHolder>>> apply() {
        return Observable.just(Pair.of(getChange(), ModelActionHolder.allModels(TitusModelUpdateActions.removeTask(getChange().getId(), getChange().getTrigger(), MESSAGE))));
    }
}
