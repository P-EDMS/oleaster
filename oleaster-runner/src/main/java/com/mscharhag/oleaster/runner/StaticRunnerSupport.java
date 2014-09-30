/*
* Copyright 2014 Michael Scharhag
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.mscharhag.oleaster.runner;

import com.mscharhag.oleaster.runner.suite.SuiteBuilder;

/**
 * {@code StaticRunnerSupport} gives access to a {@link com.mscharhag.oleaster.runner.suite.SuiteBuilder}
 * using static methods.
 * <p>Using {@code StaticRunnerSupport} is the preferred way to build a test suite.
 * For fluent access it is recommended to statically import the methods of this class.
 */
public class StaticRunnerSupport {

	private StaticRunnerSupport() {

	}

	private static SuiteBuilder suiteBuilder;

	static void setSuiteBuilder(SuiteBuilder sb) {
		suiteBuilder = sb;
	}

	/**
	 * Creates a new test suite.
	 * <p>Test suites can contain:
	 * <ul>
	 *     	<li>specs (defined by using {@code it()}</li>
	 *     	<li>other suites (defined by nesting {@code describe()} calls)</li>
	 * 		<li>{@code beforeEach()} and {@code afterEach()} handlers.</li>
	 * </ul>
	 * <p>For example:
	 * <pre>{@code
	 * describe("my test suite", () -> {
	 *     ...
	 * });
	 * }</pre>
	 * @param text A description of the test suite
	 * @param block A code block that represents the test suite
	 */
	public static void describe(String text, Invokable block) {
		failIfNoSuiteBuilderAvailable("describe");
		suiteBuilder.describe(text, block);
	}


	/**
	 * Create a new spec.
	 * <p>Specs are used to validate a specific test condition.
	 * <p>For example:
	 * <pre>{@code
	 * it("returns a list containing one item", () -> {
	 *   assertEquals(1, getList().size());
	 * });
	 * }</pre>
	 * @param text A description of the expected behavior
	 * @param block A code block that implements the validation
	 */
	public static void it(String text, Invokable block) {
		failIfNoSuiteBuilderAvailable("it");
		suiteBuilder.it(text, block);
	}


	/**
	 * Creates a new {@code beforeEach} handler for the surrounding test suite.
	 * <p>For example:
	 * <pre>{@code
	 * beforeEach(() -> {
	 *   // this code runs before each spec
	 * });
	 * }</pre>
	 * @param block A code block that is executed before every spec execution
	 */
	public static void beforeEach(Invokable block) {
		failIfNoSuiteBuilderAvailable("beforeEach");
		suiteBuilder.beforeEach(block);
	}

	/**
	 * Creates a new {@code afterEach} handler for the surrounding test suite.
	 * <p>For example:
	 * <pre>{@code
	 * afterEach(() -> {
	 *   // this code runs after each spec
	 * });
	 * }</pre>
	 * @param block A code block that is executed after every spec execution
	 */
	public static void afterEach(Invokable block) {
		failIfNoSuiteBuilderAvailable("afterEach");
		suiteBuilder.afterEach(block);
	}


	private static void failIfNoSuiteBuilderAvailable(String methodName) {
		if (suiteBuilder == null) {
			throw new IllegalStateException(String.format("No suiteBuilder available, " +
					"maybe you called %s() in a location where it is not intended to be called?", methodName));
		}
	}
}
