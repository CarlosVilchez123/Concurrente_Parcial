import socket

def Conecction():

    server_address = ('192.168.100.25', 4444)

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        client_socket.connect(server_address)

        message = [10.0,3,0,60]
        client_socket.sendall(message.encode())

        data = client_socket.recv(1024)
        print("Respuesta del servidor Java:", data.decode())

        while msg!="salir":
            msg = input("NO SE ESTO ES DE EJEMPLO")
            print("Hola")
    except:
        print("hubo un error de conexion")
def main():
    Conecction()

if __name__  == '__main__':
    main()