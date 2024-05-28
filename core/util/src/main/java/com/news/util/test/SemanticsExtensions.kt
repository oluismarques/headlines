package com.news.util.test

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed

fun SemanticsNodeInteractionCollection.assertExists(): SemanticsNodeInteractionCollection {
    fetchSemanticsNodes().forEachIndexed { index, _ ->
        get(index).assertExists()
    }
    return this
}

fun SemanticsNodeInteractionCollection.assertAreDisplayed(): SemanticsNodeInteractionCollection {
    fetchSemanticsNodes().forEachIndexed { index, _ ->
        get(index).assertIsDisplayed()
    }
    return this
}

fun SemanticsNodeInteractionCollection.assertHasClickAction(): SemanticsNodeInteractionCollection {
    fetchSemanticsNodes().forEachIndexed { index, _ ->
        get(index).assertHasClickAction()
    }
    return this
}