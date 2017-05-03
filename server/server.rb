require_relative 'environment'
$:.push('../gen-rb')
require_relative '../gen-rb/hospital_types'
require_relative '../gen-rb/hospital_constants'
require_relative '../gen-rb/hospital_service'

class Server
  CONFIG_FILE = 'config/config.yml'.freeze

  def initialize
    @config = YAML.load(File.open(CONFIG_FILE))
  end

  def run
    handler = HospitalHandler.new
    processor = ::HospitalService::Processor.new(handler)

    puts "Starting NonBlockingServier on #{@config['non_blocking_server_socket']}..."
    transport_factory = Thrift::FramedTransportFactory.new
    transport = Thrift::ServerSocket.new(@config['non_blocking_server_socket'])
    non_blocking_server = Thrift::NonblockingServer.new(processor, transport, transport_factory)
    Thread.new do
      non_blocking_server.serve
    end

    puts "Starting TThreadPoolServer on #{@config['server_socket']}..."
    transport_factory = Thrift::BufferedTransportFactory.new

    transport = Thrift::ServerSocket.new(@config['server_socket'])
    thread_server = Thrift::ThreadPoolServer.new(processor, transport, transport_factory)
    thread_server.serve

    puts '!!!!!Servers closed!!!!!'
  ensure
    non_blocking_server.shutdown if non_blocking_server
    thread_server.shutdown if thread_server
  end
end

# begin
  Server.new.run
# rescue Exception
#   puts 'Bye :('
# end
