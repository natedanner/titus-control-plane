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

package io.netflix.titus.master.jobmanager.service.event;

import io.netflix.titus.api.jobmanager.model.job.Job;
import io.netflix.titus.master.jobmanager.service.common.action.JobChange;
import io.netflix.titus.master.jobmanager.service.common.action.TitusChangeAction;

/**
 */
public abstract class JobChangeReconcilerEvent extends JobManagerReconcilerEvent {

    private final TitusChangeAction changeAction;

    protected JobChangeReconcilerEvent(Job<?> job, TitusChangeAction changeAction, long transactionId) {
        super(job, transactionId);
        this.changeAction = changeAction;
    }

    public TitusChangeAction getChangeAction() {
        return changeAction;
    }

    public static class JobBeforeChangeReconcilerEvent extends JobChangeReconcilerEvent {

        public JobBeforeChangeReconcilerEvent(Job<?> job, TitusChangeAction changeAction, long transactionId) {
            super(job, changeAction, transactionId);
        }
    }

    public static class JobAfterChangeReconcilerEvent extends JobChangeReconcilerEvent {

        private final JobChange jobChange;
        private final long executionTimeMs;

        public JobAfterChangeReconcilerEvent(Job<?> job, TitusChangeAction changeAction, JobChange jobChange, long executionTimeMs, long transactionId) {
            super(job, changeAction, transactionId);
            this.jobChange = jobChange;
            this.executionTimeMs = executionTimeMs;
        }

        public JobChange getJobChange() {
            return jobChange;
        }

        public long getExecutionTimeMs() {
            return executionTimeMs;
        }
    }

    public static class JobChangeErrorReconcilerEvent extends JobChangeReconcilerEvent {

        private final Throwable error;
        private final long executionTimeMs;

        public JobChangeErrorReconcilerEvent(Job<?> job, TitusChangeAction changeAction, Throwable error, long executionTimeMs, long transactionId) {
            super(job, changeAction, transactionId);
            this.error = error;
            this.executionTimeMs = executionTimeMs;
        }

        public long getExecutionTimeMs() {
            return executionTimeMs;
        }

        public Throwable getError() {
            return error;
        }
    }
}
