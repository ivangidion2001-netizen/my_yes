package com.application.yes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
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
fun CustomerSearchScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    budget: String,
    onBudgetChange: (String) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onViewShops: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Kariakoo Smart Search", fontSize = 16.sp, fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Customer Hub",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4F46E5),
                modifier = Modifier
                    .background(Color(0xFFEEF2FF), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
            Text(
                text = "Customer Portal",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Search Your Product",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("What are you buying today? e.g. Charger") },
                trailingIcon = {
                    IconButton(onClick = onNext, modifier = Modifier.background(Color(0xFF4F46E5), RoundedCornerShape(8.dp))) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = budget,
                onValueChange = onBudgetChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your budget (TSh)") },
                label = { Text("Your Budget") },
                leadingIcon = { Text("TSh", modifier = Modifier.padding(start = 12.dp), fontWeight = FontWeight.Bold, color = Color(0xFF64748B)) },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Kariakoo Hot Keywords", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8))
            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Charger", "Kitenge", "Cloves", "Sandal", "Rice").forEach { term ->
                    SuggestionChip(term = term, onClick = { 
                        onSearchQueryChange(term)
                        onNext()
                    })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEF2FF), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF4F46E5), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Search indexes allow comparison across three standardized marketplace quality classification indexes (A, B & C) immediately.",
                    fontSize = 11.sp,
                    color = Color(0xFF1E293B),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onViewShops,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF4F46E5))
            ) {
                Text("List of Registered Shops", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5))
            ) {
                Text("Proceed to Price Grades", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun SuggestionChip(term: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        color = Color.White
    ) {
        Text(
            text = term,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomerSearchScreenPreview() {
    MaterialTheme {
        CustomerSearchScreen(
            searchQuery = "Charger",
            onSearchQueryChange = {},
            budget = "5000",
            onBudgetChange = {},
            onBack = {},
            onNext = {},
            onViewShops = {}
        )
    }
}
