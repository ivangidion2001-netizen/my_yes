package com.application.yes.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.yes.Shop
import com.application.yes.initialShops
import com.application.yes.marketSectors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    shopId: String?,
    shops: List<Shop>,
    onBack: () -> Unit,
    onRestart: () -> Unit
) {
    val shop = shops.find { it.id == shopId } ?: shops[0]
    var isSimulating by remember { mutableStateOf(false) }
    
    val infiniteTransition = rememberInfiniteTransition(label = "ping")
    val pingScale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pingScale"
    )
    val pingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pingAlpha"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Market Micro-Map", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text(
                "Step 4: Micro-Navigation",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4F46E5),
                modifier = Modifier
                    .background(Color(0xFFEEF2FF), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
            Text(
                "Shop Location",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                "Locating ${shop.name} in Kariakoo.",
                fontSize = 12.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF0F172A))
                    .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(24.dp))
            ) {
                // Map Background grid
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val step = 40.dp.toPx()
                    for (x in 0..size.width.toInt() step step.toInt()) {
                        drawLine(Color(0xFF1E293B), Offset(x.toFloat(), 0f), Offset(x.toFloat(), size.height), strokeWidth = 1f)
                    }
                    for (y in 0..size.height.toInt() step step.toInt()) {
                        drawLine(Color(0xFF1E293B), Offset(0f, y.toFloat()), Offset(size.width, y.toFloat()), strokeWidth = 1f)
                    }
                }

                // Simulated Sectors and Pins
                marketSectors.forEach { sector ->
                    val isTarget = shop.location.contains(sector.name.split(" ")[0], ignoreCase = true)
                    
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 40.dp, vertical = 40.dp)
                    ) {
                        // Simplified positioning based on percentages
                        val x = sector.cx.removeSuffix("%").toFloat() / 100f
                        val y = sector.cy.removeSuffix("%").toFloat() / 100f

                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(x = (x - 0.5f).dp * 300, y = (y - 0.5f).dp * 400),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isTarget) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(Color(0xFF818CF8).copy(alpha = pingAlpha), RoundedCornerShape(16.dp))
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(if (isTarget) Color(0xFF4F46E5) else Color(0xFF334155), RoundedCornerShape(8.dp))
                                    .border(2.dp, Color.White, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                            }
                            Text(
                                text = sector.name.split(" ")[0],
                                color = Color(0xFF94A3B8),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 24.dp)
                            )
                        }
                    }
                }

                // HUD Bottom Overlay
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(12.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF020617).copy(alpha = 0.95f)),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Active Stall Destination", fontSize = 8.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.Bold)
                            Text(shop.name, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFFBBF24), maxLines = 1)
                            Text(shop.location, fontSize = 8.sp, color = Color(0xFF64748B))
                        }
                        Button(
                            onClick = { isSimulating = true },
                            colors = ButtonDefaults.buttonColors(containerColor = if (isSimulating) Color(0xFF1E293B) else Color(0xFF4F46E5)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isSimulating) "Simulating..." else "Get Directions", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRestart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A))
            ) {
                Text("Restart Walkthrough", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MaterialTheme {
        MapScreen(
            shopId = "shop_1",
            shops = initialShops,
            onBack = {},
            onRestart = {}
        )
    }
}
