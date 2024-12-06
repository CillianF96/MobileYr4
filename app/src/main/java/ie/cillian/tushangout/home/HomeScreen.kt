package ie.cillian.tushangout.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFA726), Color(0xFF212121)) // Orange to Black gradient
                )
            )
    ) {
        Column {
            // App Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "TUSHangOut",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                IconButton(onClick = { /* TODO: Handle refresh */ }) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp, // Requires "androidx.compose.material.icons.filled.Refresh"
                        contentDescription = "Logout",
                        tint = Color.Black
                    )
                }
            }

            // List of Items
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight(0.9f) // Adjusts for FAB space
            ) {
                items(10) { index -> // Replace 10 with the number of items in your list
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar Icon
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFFEDE7F6), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "A", fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Header and Subheader
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Header",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Subhead",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }

                            // Icons
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                IconButton(onClick = { /* TODO: Handle action */ }) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowUp, // Requires "androidx.compose.material.icons.filled.ArrowUpward"
                                        contentDescription = "Upvote",
                                        tint = Color.Gray
                                    )
                                }
                                IconButton(onClick = { /* TODO: Handle action */ }) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown, // Requires "androidx.compose.material.icons.filled.ArrowDownward"
                                        contentDescription = "Downvote",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { /* TODO: Handle FAB click */ },
            containerColor = Color.Black,
            contentColor = Color(0xFFFFA726),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add, // Requires "androidx.compose.material.icons.filled.Add"
                contentDescription = "Add"
            )
        }
    }
}
