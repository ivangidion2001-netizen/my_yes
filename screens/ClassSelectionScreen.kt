package com.application.yes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassSelectionScreen(
    searchQuery: String,
    budget: String,
    onBack: () -> Unit,
    onClassSelect: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quality Class Filter", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                text = "Step 2: Quality Filtering",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4F46E5),
                modifier = Modifier
                    .background(Color(0xFFEEF2FF), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
            Text(
                text = "Choose Your Class",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = if (budget.isNotEmpty()) 
                    "Comparing values for \"$searchQuery\" with a budget of $budget TSh. Under which tier do you wish to view shops?"
                else 
                    "Comparing values for \"$searchQuery\". Under which tier do you wish to view shops?",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            ClassCard(
                letter = "A",
                title = "Class A (Premium Quality)",
                description = "Top imported standard grades, pristine seals, certified safety codes & official shop guarantees.",
                color = Color(0xFFF59E0B),
                onClick = { onClassSelect("A") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ClassCard(
                letter = "B",
                title = "Class B (Standard Grade)",
                description = "Excellent durable utility materials, locally optimized prices, ideal value performance balances.",
                color = Color(0xFF4F46E5),
                onClick = { onClassSelect("B") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ClassCard(
                letter = "C",
                title = "Class C (Economy / Bulk)",
                description = "Mass supply, wholesale price models, minimal packaging layout, perfect for light budget projects.",
                color = Color(0xFF10B981),
                onClick = { onClassSelect("C") }
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF7ED), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "In Kariakoo Market, pricing structures vary immensely based on quality class. Picking a tier ensures you view only compliant vendors.",
                    fontSize = 11.sp,
                    color = Color(0xFF92400E),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun ClassCard(
    letter: String,
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        color = Color.White
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .border(1.dp, color.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(letter, color = color, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))
                Text(description, fontSize = 10.sp, color = Color(0xFF64748B), lineHeight = 14.sp)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFCBD5E1))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClassSelectionScreenPreview() {
    MaterialTheme {
        ClassSelectionScreen(
            searchQuery = "Charger",
            budget = "15000",
            onBack = {},
            onClassSelect = {}
        )
    }
}
