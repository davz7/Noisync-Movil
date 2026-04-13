package mx.edu.noisync.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class AuthenticatedDestination {
    SONGS,
    MY_SONGS,
    TEAM,
    INSTRUMENTS,
    PROFILE
}

@Composable
fun AuthenticatedBottomBar(
    selectedDestination: AuthenticatedDestination,
    onOpenSongs: () -> Unit,
    onOpenMySongs: () -> Unit,
    onOpenTeam: () -> Unit,
    onOpenInstruments: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            HorizontalDivider(color = Color(0xFFE5E7EB), thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AuthenticatedBottomBarItem(
                    label = "Canciones",
                    isSelected = selectedDestination == AuthenticatedDestination.SONGS,
                    onClick = onOpenSongs
                )
                AuthenticatedBottomBarItem(
                    label = "Mis canciones",
                    isSelected = selectedDestination == AuthenticatedDestination.MY_SONGS,
                    onClick = onOpenMySongs
                )
                AuthenticatedBottomBarItem(
                    label = "Musicos",
                    isSelected = selectedDestination == AuthenticatedDestination.TEAM,
                    onClick = onOpenTeam
                )
                AuthenticatedBottomBarItem(
                    label = "Instrumentos",
                    isSelected = selectedDestination == AuthenticatedDestination.INSTRUMENTS,
                    onClick = onOpenInstruments
                )
                AuthenticatedBottomBarItem(
                    label = "Perfil",
                    isSelected = selectedDestination == AuthenticatedDestination.PROFILE,
                    onClick = onOpenProfile
                )
            }
        }
    }
}

@Composable
private fun AuthenticatedBottomBarItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isSelected) Color.Black else Color(0xFF6B7280)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(3.dp)
                .background(
                    color = if (isSelected) Color.Black else Color.Transparent,
                    shape = RoundedCornerShape(999.dp)
                )
        )
        Text(
            text = label,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
        )
    }
}
