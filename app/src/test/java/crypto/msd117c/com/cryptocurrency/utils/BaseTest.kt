package crypto.msd117c.com.cryptocurrency.utils

import org.junit.After

abstract class BaseTest constructor(size: Int) {
    val ANSI_GREEN = "\u001B[32m"
    val ANSI_RESET = "\u001B[0m"

    companion object {
        var testCase = 0
        var testsPassed = 0
    }

    protected val testResults = Array(size) { false }

    @After
    fun displayTestResults() {
        if (testCase == 0) {
            println()
            println("Running ${this::class.java.simpleName} tests:")
            println()
        }

        var testCaseNumber = (testCase + 1).toString()
        if (testCaseNumber.length == 1) {
            testCaseNumber = "0$testCaseNumber"
        }
        if (testResults[testCase]) {
            testsPassed++
            println("${ANSI_GREEN}Test #$testCaseNumber: OK $ANSI_RESET")
        } else {
            System.err.println("Test #$testCaseNumber: ERR")
        }
        testCase++
        if (testCase == testResults.size) {
            println()
            println(
                "${this::class.java.simpleName} tests ended with $testsPassed/${testResults.size} (${
                if (testsPassed == 0 || testResults.isEmpty()) {
                    0
                } else {
                    (testResults.size / testsPassed) * 100
                }
                }%)"
            )
            println()
            println("----------------------------------------------------------------------------------")
            println()
            println()
            testCase = 0
            testsPassed = 0
        }
    }
}