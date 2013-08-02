package server.clientCompiler;

import java.rmi.*;

public interface CompilerRemote extends Remote {

    public String compileAndJar(String userName, String macAddress) throws RemoteException;
}
