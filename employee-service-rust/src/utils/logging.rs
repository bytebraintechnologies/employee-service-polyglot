use std::fs;
use std::path::Path;
use tracing::{info, Level};
use tracing_appender::rolling;
use tracing_subscriber::{
    fmt::format::FmtSpan,
    EnvFilter,
};

pub fn init_logging() {
    // Create logs directory if it doesn't exist
    let logs_dir = Path::new("logs");
    if !logs_dir.exists() {
        fs::create_dir_all(logs_dir).expect("Failed to create logs directory");
    }

    // Setup file appenders for rolling logs
    let file_appender = rolling::daily(logs_dir, "app.log");
    let (non_blocking, _guard) = tracing_appender::non_blocking(file_appender);

    let error_file_appender = rolling::daily(logs_dir, "error.log");
    let (error_non_blocking, _error_guard) = tracing_appender::non_blocking(error_file_appender);

    // Initialize the global default subscriber
    tracing_subscriber::fmt()
        // Use default timer instead of LocalTime (which requires local-time feature)
        .with_env_filter(
            EnvFilter::builder()
                .with_default_directive(Level::INFO.into())
                .from_env_lossy(),
        )
        .with_span_events(FmtSpan::CLOSE)
        // Add the file writer for general logs (INFO and above)
        .with_writer(non_blocking)
        // Add console output
        .with_writer(std::io::stdout)
        .init();

    info!("Logging system initialized");
}
