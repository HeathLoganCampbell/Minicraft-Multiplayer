package dev.cobblesword.ld22.server;

public class MinicraftServer implements Runnable
{
    @Override
    public void run()
    {
        while(true)
        {

        }
    }

    public static void main(String[] args)
    {
        MinicraftServer server = new MinicraftServer();
        server.run();
    }
}
