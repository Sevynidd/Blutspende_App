package de.agb.blutspende_app.navbar

import de.agb.blutspende_app.ui.NavigationBar
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class NavBarButtonsInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testNavigationBarDashboard() {

        rule.setContent {
            NavigationBar()
        }

        rule.onNode(
            hasText("Dashboard")
                    and
                    hasClickAction()
        ).performClick()

        rule.onNode(
            hasText("Dashboard")
                    and
                    hasClickAction()
        ).assertIsDisplayed()
    }

    @Test
    fun testNavigationBarAusweis() {

        rule.setContent {
            NavigationBar()
        }

        val nodeAusweis = rule.onAllNodes(
            hasAnySibling(hasClickAction())
        ).filter(hasClickAction())[1]

        nodeAusweis.assertIsDisplayed()

        nodeAusweis.performClick()

        nodeAusweis.assertTextEquals("Ausweis")
    }

    @Test
    fun testNavigationBarWerte() {

        rule.setContent {
            NavigationBar()
        }

        val nodeWerte = rule.onAllNodes(
            hasAnySibling(hasClickAction())
        ).filter(hasClickAction())[2]

        nodeWerte.assertIsDisplayed()

        nodeWerte.performClick()

        nodeWerte.assertTextEquals("Werte")
    }

    @Test
    fun testNavigationBarVorrat() {

        rule.setContent {
            NavigationBar()
        }

        val nodeVorrat = rule.onAllNodes(
            hasAnySibling(hasClickAction())
        ).filter(hasClickAction())[3]

        nodeVorrat.assertIsDisplayed()

        nodeVorrat.performClick()

        nodeVorrat.assertTextEquals("Vorrat")
    }
}