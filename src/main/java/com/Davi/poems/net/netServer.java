package com.Davi.poems.net;

import com.Davi.poems.basic.event;
import com.Davi.poems.tools.myException;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import javax.net.ssl.SSLServerSocket;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Davi.poems.basic.operation.isSearch;
import static com.Davi.poems.basic.operation.pairMatch;
import static com.Davi.poems.basic.operation.possibleSearch;


/**
 * Created by David on 2017/4/12.
 */
public class netServer {
    Logger log = Logger.getLogger(netServer.class);
    String jetty_home = "tang";
    String host = "127.0.0.1";
    Server server;
    handler handler1 = new handler();
    public netServer(String p){

        /* 开始ssl验证，需要的读取配置文件，来自apache flume httpSource
        if (sslEnabled) {
            LOG.debug("SSL configuration enabled");
            keyStorePath = context.getString(HTTPSourceConfigurationConstants.SSL_KEYSTORE);
            Preconditions.checkArgument(keyStorePath != null && !keyStorePath.isEmpty(),
                    "Keystore is required for SSL Conifguration" );
            keyStorePassword =
                    context.getString(HTTPSourceConfigurationConstants.SSL_KEYSTORE_PASSWORD);
            Preconditions.checkArgument(keyStorePassword != null,
                    "Keystore password is required for SSL Configuration");
            String excludeProtocolsStr =
                    context.getString(HTTPSourceConfigurationConstants.EXCLUDE_PROTOCOLS);
            if (excludeProtocolsStr == null) {
                excludedProtocols.add("SSLv3");
            } else {
                excludedProtocols.addAll(Arrays.asList(excludeProtocolsStr.split(" ")));
                if (!excludedProtocols.contains("SSLv3")) {
                    excludedProtocols.add("SSLv3");
                }
            }
        }*/
        int port = Integer.valueOf(p);

        server = new Server();
        Connector connector = new SelectChannelConnector();
        connector.setHost(host);
        connector.setPort(port);
        server.addConnector(connector);

        org.mortbay.jetty.servlet.Context root = new Context(server, "/", org.mortbay.jetty.servlet.Context.SESSIONS);
        root.addServlet(new ServletHolder(new httpServlet()), "/");
        HTTPServerConstraintUtil.enforceConstraints(root);
        try {
            server.start();
            log.info("server启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("server启动失败 \n"+e);
        }
        //System.out.println("启动成功");


    }
    public void stop() {
        try {
            server.stop();
            server.join();
            server = null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("server停止失败 \n"+e);
        }


    }

    public static void main(String[] args) {
        netServer ns = new netServer("8888");
    }

    private class httpServlet extends HttpServlet{

        Logger logger = Logger.getLogger(httpServlet.class);
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // 处理一项http请求，解析httpRequest,编写response
            try {
                request.setCharacterEncoding("UTF-8");
                event e = handler1.getEvent(request);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                OutputStream ops = response.getOutputStream();
                ObjectOutputStream oout = new ObjectOutputStream(ops);
                oout.writeObject(e);
                oout.flush();
                response.flushBuffer();
            }catch (myException e) {
                e.printStackTrace();
                logger.warn(e);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"一个非法的请求，请检查request格式 \n"+e.getMessage());
                return;
            }
        }
        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response)
                throws IOException {
            doPost(request, response);
        }
    }
    //ssl内容
    private static class SocketConnector extends SslSocketConnector {
        private final List<String> excludedProtocols;

        SocketConnector(List<String> excludedProtocols) {
            this.excludedProtocols = excludedProtocols;
        }

        @Override
        public ServerSocket newServerSocket(String host, int port, int backlog) throws IOException {
            SSLServerSocket socket = (SSLServerSocket)super.newServerSocket(host, port, backlog);
            String[] protocols = socket.getEnabledProtocols();
            List<String> newProtocols = new ArrayList<String>(protocols.length);
            for (String protocol: protocols) {
                if (!excludedProtocols.contains(protocol)) {
                    newProtocols.add(protocol);
                }
            }
            socket.setEnabledProtocols(newProtocols.toArray(new String[newProtocols.size()]));
            return socket;
        }
    }


}
