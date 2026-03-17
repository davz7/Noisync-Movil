package mx.edu.noisync.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.edu.noisync.fake.FakeMusicians
import mx.edu.noisync.ui.components.BackButton

@Composable
fun UserTeamScreen(navController: NavController) {
    val musicians = FakeMusicians.allMusicians

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FA)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                BackButton(onClick = { navController.popBackStack() })
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Musicos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(musicians) { musician ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color(musician.avatarColor), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = musician.initials,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = musician.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = musician.instrument,
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "  •  ",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = musician.role,
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            val (badgeBgColor, badgeTextColor) = when (musician.status) {
                                "Activo" -> Color(0xFFD1FAE5) to Color(0xFF059669)
                                else -> Color(0xFFFEF3C7) to Color(0xFFD97706)
                            }

                            Surface(
                                color = badgeBgColor,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = musician.status,
                                    color = badgeTextColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserTeamScreenPreview() {
    UserTeamScreen(rememberNavController())
}
