require_relative 'environment'
$:.push('../gen-rb')
require_all '../gen-rb'
require 'thrift'

class Server
  CONFIG_FILE = 'config/config.yml'.freeze

  def initialize
    @config = YAML.load(File.open(CONFIG_FILE))
  end

  def run
    handler = HospitalHandler.new
    processor = ::HospitalService::Processor.new(handler)
    transport = Thrift::ServerSocket.new(@config['server_socket'])
    transport_factory = Thrift::BufferedTransportFactory.new
    server = Thrift::SimpleServer.new(processor, transport, transport_factory)

    puts "Starting the server on #{@config['server_socket']}..."
    server.serve
    puts '!!!!!Server closed!!!!!'
  end
end

begin
  Server.new.run
rescue Exception
  puts 'Bye :('
end
