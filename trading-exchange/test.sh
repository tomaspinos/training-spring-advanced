#!/usr/bin/env bash

curl -X POST http://localhost:8080/user/john
curl -X POST http://localhost:8080/user/sam
curl -X POST http://localhost:8080/user/ron

curl -X PUT http://localhost:8080/user/john/balance/EUR/1000
curl -X PUT http://localhost:8080/user/sam/balance/CZK/26000
curl -X PUT http://localhost:8080/user/ron/balance/CZK/26000

curl -X POST http://localhost:8080/order/buy/sam/EUR/CZK/60/26
curl -X POST http://localhost:8080/order/buy/ron/EUR/CZK/40/26
curl -X POST http://localhost:8080/order/sell/john/CZK/EUR/100/26
