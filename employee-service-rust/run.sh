#!/bin/bash

echo "Building and running Employee Service Rust..."
cargo build --release
./target/release/employee-service-rust
