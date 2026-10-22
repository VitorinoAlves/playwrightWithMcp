---
description: Incremental strategy for E2E test automation from .feature (BDD) files
globs: "src/test/**/*.feature, tests/**/*.feature, **/*.feature"
alwaysApply: false
---

# Skill: Incremental E2E Test Automation (BDD)

This skill must be followed whenever requested to automate, create step definitions, or implement tests for a `.feature` file.

## Fundamental Premise
**Work strictly on one scenario at a time and one step at a time.** Never attempt to automate multiple scenarios or all steps in a single code block.

---

## Executive Automation Workflow

### Phase 1: Decomposition and Planning
1. Read the selected `.feature` file.
2. Create a simple action plan (Checklist) listing the scenarios in the ideal order of implementation (from simplest/independent to most complex).
3. Present to the user which Scenario will be the starting point.

### Phase 2: Granular Execution (Per Scenario and Step)

For the **current Scenario**:

1. **Step Selection:**
    - Take the next pending `Step` (`Given`, `When`, `Then`).

2. **Step Implementation:**
    - Create or update the corresponding **Step Definition** for the selected step.
    - Write or adjust necessary test abstraction layers (Page Objects, Fixtures, or Actions) required solely to support this step.

3. **Step Validation:**
    - Ensure there are no compilation or syntax errors in the modified files.

4. **Iteration until Completion:**
    - Repeat the selection, implementation, and validation steps until **all steps** in the current scenario are fully coded.

5. **Full Scenario Validation:**
    - Run the test runner in the terminal focusing **only** on the newly automated scenario (e.g., using a `@tag` or filtering by scenario name).
    - Confirm that the test passed (status *Passed/Green*).

### Phase 3: Transition
- Only when the current scenario is 100% implemented and passing successfully, move on to the next scenario on the Phase 1 list.

---

## E2E Coding Best Practices
- Reuse existing Page Objects and locators before creating new ones.
- Keep Step Definition methods clean, delegating page interaction logic to support/page classes.
- Ensure assertions are clear and tightly aligned with the step's intent.