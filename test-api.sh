#!/bin/bash

# Test Script for Hexagonal Architecture Spring Boot 3 Example
# This script demonstrates all API endpoints

set -e  # Exit on error

echo "=========================================="
echo "Testing Hexagonal Architecture API"
echo "=========================================="
echo ""

# Check if jq is installed
if ! command -v jq &> /dev/null; then
    echo "Warning: jq is not installed. Output will not be formatted."
    echo "Install jq with: sudo apt-get install jq"
    JQ_AVAILABLE=false
else
    JQ_AVAILABLE=true
fi

BASE_URL="http://localhost:8080/api/products"

echo "1. Creating a Laptop..."
LAPTOP_RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "High performance laptop",
    "price": 999.99,
    "stock": 10
  }')

if [ "$JQ_AVAILABLE" = true ]; then
    echo $LAPTOP_RESPONSE | jq .
    LAPTOP_ID=$(echo $LAPTOP_RESPONSE | jq -r '.id')
else
    echo $LAPTOP_RESPONSE
    LAPTOP_ID=$(echo $LAPTOP_RESPONSE | grep -o '"id":"[^"]*"' | cut -d'"' -f4)
fi

if [ -z "$LAPTOP_ID" ] || [ "$LAPTOP_ID" = "null" ]; then
    echo "Error: Failed to create laptop"
    exit 1
fi
echo ""

echo "2. Creating a Mouse..."
MOUSE_RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mouse",
    "description": "Wireless gaming mouse",
    "price": 49.99,
    "stock": 25
  }')

if [ "$JQ_AVAILABLE" = true ]; then
    echo $MOUSE_RESPONSE | jq .
    MOUSE_ID=$(echo $MOUSE_RESPONSE | jq -r '.id')
else
    echo $MOUSE_RESPONSE
    MOUSE_ID=$(echo $MOUSE_RESPONSE | grep -o '"id":"[^"]*"' | cut -d'"' -f4)
fi

if [ -z "$MOUSE_ID" ] || [ "$MOUSE_ID" = "null" ]; then
    echo "Error: Failed to create mouse"
    exit 1
fi
echo ""

echo "3. Getting all products..."
if [ "$JQ_AVAILABLE" = true ]; then
    curl -s $BASE_URL | jq .
else
    curl -s $BASE_URL
fi
echo ""

echo "4. Getting Laptop by ID..."
if [ "$JQ_AVAILABLE" = true ]; then
    curl -s $BASE_URL/$LAPTOP_ID | jq .
else
    curl -s $BASE_URL/$LAPTOP_ID
fi
echo ""

echo "5. Decreasing Laptop stock by 3..."
if [ "$JQ_AVAILABLE" = true ]; then
    curl -s -X POST "$BASE_URL/$LAPTOP_ID/decrease-stock?quantity=3" | jq .
else
    curl -s -X POST "$BASE_URL/$LAPTOP_ID/decrease-stock?quantity=3"
fi
echo ""

echo "6. Updating Laptop details..."
if [ "$JQ_AVAILABLE" = true ]; then
    curl -s -X PUT $BASE_URL/$LAPTOP_ID \
      -H "Content-Type: application/json" \
      -d '{
        "name": "Gaming Laptop",
        "description": "High performance gaming laptop with RTX 4090",
        "price": 1299.99,
        "stock": 5
      }' | jq .
else
    curl -s -X PUT $BASE_URL/$LAPTOP_ID \
      -H "Content-Type: application/json" \
      -d '{
        "name": "Gaming Laptop",
        "description": "High performance gaming laptop with RTX 4090",
        "price": 1299.99,
        "stock": 5
      }'
fi
echo ""

echo "7. Getting all products after updates..."
if [ "$JQ_AVAILABLE" = true ]; then
    curl -s $BASE_URL | jq .
else
    curl -s $BASE_URL
fi
echo ""

echo "8. Deleting Mouse..."
curl -s -X DELETE $BASE_URL/$MOUSE_ID
echo "Mouse deleted successfully"
echo ""

echo "9. Getting all products after deletion..."
if [ "$JQ_AVAILABLE" = true ]; then
    curl -s $BASE_URL | jq .
else
    curl -s $BASE_URL
fi
echo ""

echo "=========================================="
echo "All tests completed successfully!"
echo "=========================================="
