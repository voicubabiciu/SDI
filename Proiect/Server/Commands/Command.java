package Proiect.Server.Commands;

public interface Command<T, P> {
    public T executeCommand(P args);
}
