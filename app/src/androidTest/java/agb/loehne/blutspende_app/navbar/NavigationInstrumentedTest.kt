package agb.loehne.blutspende_app.navbar

import agb.loehne.blutspende_app.ui.NavigationBar
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class NavigationInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testNavigationDashboard() {

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
           hasNoClickAction()
       ).assertIsDisplayed()
    }

    @Test
    fun testNavigationAusweis() {

        rule.setContent {
            NavigationBar()
        }

        val nodeAusweis = rule.onAllNodes(
            hasAnySibling(hasClickAction())
        ).filter(hasClickAction())[1]

        nodeAusweis.performClick()

        rule.onNode(
            hasText("Ausweis")
                    and
                    hasNoClickAction()
        ).assertIsDisplayed()
    }

    @Test
    fun testNavigationWerte() {

        rule.setContent {
            NavigationBar()
        }

        val nodeWerte = rule.onAllNodes(
            hasAnySibling(hasClickAction())
        ).filter(hasClickAction())[2]

        nodeWerte.performClick()

        rule.onNode(
            hasText("Werte")
                    and
                    hasNoClickAction()
        ).assertIsDisplayed()
    }

    @Test
    fun testNavigationVorrat() {

        rule.setContent {
            NavigationBar()
        }

        val nodeVorrat = rule.onAllNodes(
            hasAnySibling(hasClickAction())
        ).filter(hasClickAction())[3]

        nodeVorrat.performClick()

        rule.onNode(
            hasText("Vorrat")
                    and
                    hasNoClickAction()
        ).assertIsDisplayed()
    }
}