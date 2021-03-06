/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.failure;

import org.apache.ignite.Ignite;
import org.apache.ignite.internal.IgnitionEx;
import org.apache.ignite.internal.util.typedef.internal.U;

/**
 * Handler will stop node in case of critical error using {@code IgnitionEx.stop(nodeName, true, true)} call.
 */
public class StopNodeFailureHandler implements FailureHandler {
    /** {@inheritDoc} */
    @Override public boolean onFailure(Ignite ignite, FailureContext failureCtx) {
        new Thread(
            new Runnable() {
                @Override public void run() {
                    U.error(ignite.log(), "Stopping local node on Ignite failure: [failureCtx=" + failureCtx + ']');

                    IgnitionEx.stop(ignite.name(), true, true);
                }
            },
            "node-stopper"
        ).start();

        return true;
    }
}
