require 'logger'

# Logger configuration
class Logger
  # Singleton logger instance
  @@logger = nil

  # Initialize logger
  def self.init
    @@logger = ::Logger.new('logs/employee_service.log')
    @@logger.level = ::Logger::INFO
    @@logger.formatter = proc do |severity, datetime, progname, msg|
      date_format = datetime.strftime("%Y-%m-%d %H:%M:%S")
      "[#{date_format}] #{severity}: #{msg}\n"
    end
  end
  
  # Get logger instance
  def self.instance
    init if @@logger.nil?
    @@logger
  end
  
  # Log debug message
  def self.debug(message)
    instance.debug(message)
    puts "[DEBUG] #{message}"
  end
  
  # Log info message
  def self.info(message)
    instance.info(message)
    puts "[INFO] #{message}"
  end
  
  # Log warning message
  def self.warn(message)
    instance.warn(message)
    puts "[WARN] #{message}"
  end
  
  # Log error message
  def self.error(message)
    instance.error(message)
    puts "[ERROR] #{message}"
  end
end
