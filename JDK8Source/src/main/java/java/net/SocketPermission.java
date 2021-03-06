/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.net;

import java.util.Enumeration;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.net.InetAddress;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.PrivilegedAction;
import java.security.AccessController;
import java.security.Security;
import java.io.Serializable;
import java.io.ObjectStreamField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import sun.net.util.IPAddressUtil;
import sun.net.RegisteredDomain;
import sun.net.PortConfig;
import sun.security.util.SecurityConstants;
import sun.security.util.Debug;


/**
 * This class represents access to a network via sockets.
 * A SocketPermission consists of a
 * host specification and a set of "actions" specifying ways to
 * connect to that host. The host is specified as
 * <pre>
 *    host = (hostname | IPv4address | iPv6reference) [:portrange]
 *    portrange = portnumber | -portnumber | portnumber-[portnumber]
 * </pre>
 * The host is expressed as a DNS name, as a numerical IP address,
 * or as "localhost" (for the local machine).
 * The wildcard "*" may be included once in a DNS name host
 * specification. If it is included, it must be in the leftmost
 * position, as in "*.sun.com".
 * <p>
 * The format of the IPv6reference should follow that specified in <a
 * href="http://www.ietf.org/rfc/rfc2732.txt"><i>RFC&nbsp;2732: Format
 * for Literal IPv6 Addresses in URLs</i></a>:
 * <pre>
 *    ipv6reference = "[" IPv6address "]"
 *</pre>
 * For example, you can construct a SocketPermission instance
 * as the following:
 * <pre>
 *    String hostAddress = inetaddress.getHostAddress();
 *    if (inetaddress instanceof Inet6Address) {
 *        sp = new SocketPermission("[" + hostAddress + "]:" + port, action);
 *    } else {
 *        sp = new SocketPermission(hostAddress + ":" + port, action);
 *    }
 * </pre>
 * or
 * <pre>
 *    String host = url.getHost();
 *    sp = new SocketPermission(host + ":" + port, action);
 * </pre>
 * <p>
 * The <A HREF="Inet6Address.html#lform">full uncompressed form</A> of
 * an IPv6 literal address is also valid.
 * <p>
 * The port or portrange is optional. A port specification of the
 * form "N-", where <i>N</i> is a port number, signifies all ports
 * numbered <i>N</i> and above, while a specification of the
 * form "-N" indicates all ports numbered <i>N</i> and below.
 * The special port value {@code 0} refers to the entire <i>ephemeral</i>
 * port range. This is a fixed range of ports a system may use to
 * allocate dynamic ports from. The actual range may be system dependent.
 * <p>
 * The possible ways to connect to the host are
 * <pre>
 * accept
 * connect
 * listen
 * resolve
 * </pre>
 * The "listen" action is only meaningful when used with "localhost" and
 * means the ability to bind to a specified port.
 * The "resolve" action is implied when any of the other actions are present.
 * The action "resolve" refers to host/ip name service lookups.
 * <P>
 * The actions string is converted to lowercase before processing.
 * <p>As an example of the creation and meaning of SocketPermissions,
 * note that if the following permission:
 *
 * <pre>
 *   p1 = new SocketPermission("puffin.eng.sun.com:7777", "connect,accept");
 * </pre>
 *
 * is granted to some code, it allows that code to connect to port 7777 on
 * {@code puffin.eng.sun.com}, and to accept connections on that port.
 *
 * <p>Similarly, if the following permission:
 *
 * <pre>
 *   p2 = new SocketPermission("localhost:1024-", "accept,connect,listen");
 * </pre>
 *
 * is granted to some code, it allows that code to
 * accept connections on, connect to, or listen on any port between
 * 1024 and 65535 on the local host.
 *
 * <p>Note: Granting code permission to accept or make connections to remote
 * hosts may be dangerous because malevolent code can then more easily
 * transfer and share confidential data among parties who may not
 * otherwise have access to the data.
 *
 * <p>
 *  此类表示通过套接字访问网络SocketPermission由主机规范和一组"操作"组成,用于指定连接到该主机的方法主机被指定为
 * <pre>
 *  host =(hostname | IPv4address | iPv6reference)[：portrange] portrange = portnumber | -portnumber | po
 * rtnumber- [portnumber]。
 * </pre>
 *  主机表示为DNS名称,数字IP地址或"localhost"(对于本地计算机)通配符"*"可能在DNS名称主机规范中包含一次。如果包含它,它必须在最左边的位置,如"* suncom"
 * <p>
 * IPv6参考的格式应遵循<a href=\"http://wwwietforg/rfc/rfc2732txt\"> <i> RFC 2732：URL中的字面IPv6地址格式</i> </a>中指定的格式
 * ：。
 * <pre>
 *  ipv6reference ="["IPv6address"]"
 * /pre>
 *  例如,您可以按如下所示构造SocketPermission实例：
 * <pre>
 *  String hostAddress = inetaddressgetHostAddress(); if(inetaddress instanceof Inet6Address){sp = new SocketPermission("["+ hostAddress +"]："+ port,action); }
 *  else {sp = new SocketPermission(hostAddress +"："+ port,action); }}。
 * </pre>
 *  要么
 * <pre>
 *  String host = urlgetHost(); sp = new SocketPermission(host +"："+ port,action);
 * </pre>
 * <p>
 *  IPv6文字地址的<A HREF=\"Inet6Addresshtml#lform\">完全解压缩形式</A>也有效
 * <p>
 * 端口或端口是可选的。
 * "N"形式的端口规范,其中N是端口号,表示所有编号为N i及以上的端口,而规定形式"-N"指示所有编号为<i> N和以下的端口特殊端口值{@code 0}指整个<i>临时</i>端口范围这是固定范围的端
 * 口系统可以使用来分配动态端口。
 * 端口或端口是可选的。实际范围可以是系统依赖的。
 * <p>
 *  连接到主机的可能方法是
 * <pre>
 *  接受连接侦听解析
 * </pre>
 *  "listen"操作只有在与"localhost"一起使用时才有意义,并且意味着绑定到指定端口的能力当存在任何其他操作时,暗示"解析"操作操作"解析"是指主机/ ip名称服务查找
 * <P>
 * 操作字符串在处理之前转换为小写<p>作为SocketPermissions的创建和含义的示例,请注意,如果以下权限：
 * 
 * <pre>
 *  p1 = new SocketPermission("puffinengsuncom：7777","connect,accept");
 * </pre>
 * 
 *  被授予某些代码,它允许该代码连接到{@code puffinengsuncom}上的端口7777,并接受该端口上的连接
 * 
 *  <p>同样,如果有以下权限：
 * 
 * <pre>
 *  p2 = new SocketPermission("localhost：1024-","accept,connect,listen");
 * </pre>
 * 
 *  被授予某些代码,它允许该代码接受连接,连接到或侦听本地主机上1024和65535之间的任何端口
 * 
 * <p>注意：授予代码权限以接受或连接到远程主机可能很危险,因为恶意代码可以更容易地在可能不能访问数据的各方之间传输和共享机密数据
 * 
 * 
 * @see java.security.Permissions
 * @see SocketPermission
 *
 *
 * @author Marianne Mueller
 * @author Roland Schemers
 *
 * @serial exclude
 */

public final class SocketPermission extends Permission
    implements java.io.Serializable
{
    private static final long serialVersionUID = -7204263841984476862L;

    /**
     * Connect to host:port
     * <p>
     *  连接到主机：端口
     * 
     */
    private final static int CONNECT    = 0x1;

    /**
     * Listen on host:port
     * <p>
     *  在主机：端口上监听
     * 
     */
    private final static int LISTEN     = 0x2;

    /**
     * Accept a connection from host:port
     * <p>
     *  从host：port接受连接
     * 
     */
    private final static int ACCEPT     = 0x4;

    /**
     * Resolve DNS queries
     * <p>
     *  解析DNS查询
     * 
     */
    private final static int RESOLVE    = 0x8;

    /**
     * No actions
     * <p>
     *  无操作
     * 
     */
    private final static int NONE               = 0x0;

    /**
     * All actions
     * <p>
     *  所有操作
     * 
     */
    private final static int ALL        = CONNECT|LISTEN|ACCEPT|RESOLVE;

    // various port constants
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;
    private static final int PRIV_PORT_MAX = 1023;
    private static final int DEF_EPH_LOW = 49152;

    // the actions mask
    private transient int mask;

    /**
     * the actions string.
     *
     * <p>
     *  操作字符串
     * 
     * 
     * @serial
     */

    private String actions; // Left null as long as possible, then
                            // created and re-used in the getAction function.

    // hostname part as it is passed
    private transient String hostname;

    // the canonical name of the host
    // in the case of "*.foo.com", cname is ".foo.com".

    private transient String cname;

    // all the IP addresses of the host
    private transient InetAddress[] addresses;

    // true if the hostname is a wildcard (e.g. "*.sun.com")
    private transient boolean wildcard;

    // true if we were initialized with a single numeric IP address
    private transient boolean init_with_ip;

    // true if this SocketPermission represents an invalid/unknown host
    // used for implies when the delayed lookup has already failed
    private transient boolean invalid;

    // port range on host
    private transient int[] portrange;

    private transient boolean defaultDeny = false;

    // true if this SocketPermission represents a hostname
    // that failed our reverse mapping heuristic test
    private transient boolean untrusted;
    private transient boolean trusted;

    // true if the sun.net.trustNameService system property is set
    private static boolean trustNameService;

    private static Debug debug = null;
    private static boolean debugInit = false;

    // lazy initializer
    private static class EphemeralRange {
        static final int low = initEphemeralPorts("low", DEF_EPH_LOW);
        static final int high = initEphemeralPorts("high", PORT_MAX);
    };

    static {
        Boolean tmp = java.security.AccessController.doPrivileged(
                new sun.security.action.GetBooleanAction("sun.net.trustNameService"));
        trustNameService = tmp.booleanValue();
    }

    private static synchronized Debug getDebug() {
        if (!debugInit) {
            debug = Debug.getInstance("access");
            debugInit = true;
        }
        return debug;
    }

    /**
     * Creates a new SocketPermission object with the specified actions.
     * The host is expressed as a DNS name, or as a numerical IP address.
     * Optionally, a port or a portrange may be supplied (separated
     * from the DNS name or IP address by a colon).
     * <p>
     * To specify the local machine, use "localhost" as the <i>host</i>.
     * Also note: An empty <i>host</i> String ("") is equivalent to "localhost".
     * <p>
     * The <i>actions</i> parameter contains a comma-separated list of the
     * actions granted for the specified host (and port(s)). Possible actions are
     * "connect", "listen", "accept", "resolve", or
     * any combination of those. "resolve" is automatically added
     * when any of the other three are specified.
     * <p>
     * Examples of SocketPermission instantiation are the following:
     * <pre>
     *    nr = new SocketPermission("www.catalog.com", "connect");
     *    nr = new SocketPermission("www.sun.com:80", "connect");
     *    nr = new SocketPermission("*.sun.com", "connect");
     *    nr = new SocketPermission("*.edu", "resolve");
     *    nr = new SocketPermission("204.160.241.0", "connect");
     *    nr = new SocketPermission("localhost:1024-65535", "listen");
     *    nr = new SocketPermission("204.160.241.0:1024-65535", "connect");
     * </pre>
     *
     * <p>
     *  使用指定的操作创建新的SocketPermission对象主机表示为DNS名称或数字IP地址(可选)可以提供端口或portrange(用冒号分隔DNS名称或IP地址)
     * <p>
     *  要指定本地机器,请使用"localhost"作为<i>主机</i>。另请注意：空的主机</i> String("")等效于"localhost"
     * <p>
     * <i> actions </i>参数包含为指定主机(和端口)授予的操作的逗号分隔列表。
     * 可能的操作是"connect","listen","accept","resolve"","The <i>actions</i> parameter contains a comma-separated
     *  list of the actions granted for the specified host (and port(s)) Possible actions are \"connect\", \
     * "listen\", \"accept\", \"resolve\或者当指定其他三个中的任何三个时,那些"解析"的任何组合被自动添加。
     * <i> actions </i>参数包含为指定主机(和端口)授予的操作的逗号分隔列表。
     * <p>
     *  SocketPermission实例化的示例如下：
     * <pre>
     *  nr = new SocketPermission("wwwcatalogcom","connect"); nr = new SocketPermission("wwwsuncom：80","conn
     * ect"); nr = new SocketPermission("* suncom","connect"); nr = new SocketPermission("* edu","resolve");
     *  nr = new SocketPermission("2041602410","connect"); nr = new SocketPermission("localhost：1024-65535",
     * "listen"); nr = new SocketPermission("2041602410：1024-65535","connect");。
     * </pre>
     * 
     * 
     * @param host the hostname or IPaddress of the computer, optionally
     * including a colon followed by a port or port range.
     * @param action the action string.
     */
    public SocketPermission(String host, String action) {
        super(getHost(host));
        // name initialized to getHost(host); NPE detected in getHost()
        init(getName(), getMask(action));
    }


    SocketPermission(String host, int mask) {
        super(getHost(host));
        // name initialized to getHost(host); NPE detected in getHost()
        init(getName(), mask);
    }

    private void setDeny() {
        defaultDeny = true;
    }

    private static String getHost(String host) {
        if (host.equals("")) {
            return "localhost";
        } else {
            /* IPv6 literal address used in this context should follow
             * the format specified in RFC 2732;
             * if not, we try to solve the unambiguous case
             * <p>
             * RFC 2732中指定的格式;如果没有,我们试图解决明确的情况
             * 
             */
            int ind;
            if (host.charAt(0) != '[') {
                if ((ind = host.indexOf(':')) != host.lastIndexOf(':')) {
                    /* More than one ":", meaning IPv6 address is not
                     * in RFC 2732 format;
                     * We will rectify user errors for all unambiguious cases
                     * <p>
                     *  在RFC 2732格式;我们将纠正所有不明确情况的用户错误
                     * 
                     */
                    StringTokenizer st = new StringTokenizer(host, ":");
                    int tokens = st.countTokens();
                    if (tokens == 9) {
                        // IPv6 address followed by port
                        ind = host.lastIndexOf(':');
                        host = "[" + host.substring(0, ind) + "]" +
                            host.substring(ind);
                    } else if (tokens == 8 && host.indexOf("::") == -1) {
                        // IPv6 address only, not followed by port
                        host = "[" + host + "]";
                    } else {
                        // could be ambiguous
                        throw new IllegalArgumentException("Ambiguous"+
                                                           " hostport part");
                    }
                }
            }
            return host;
        }
    }

    private int[] parsePort(String port)
        throws Exception
    {

        if (port == null || port.equals("") || port.equals("*")) {
            return new int[] {PORT_MIN, PORT_MAX};
        }

        int dash = port.indexOf('-');

        if (dash == -1) {
            int p = Integer.parseInt(port);
            return new int[] {p, p};
        } else {
            String low = port.substring(0, dash);
            String high = port.substring(dash+1);
            int l,h;

            if (low.equals("")) {
                l = PORT_MIN;
            } else {
                l = Integer.parseInt(low);
            }

            if (high.equals("")) {
                h = PORT_MAX;
            } else {
                h = Integer.parseInt(high);
            }
            if (l < 0 || h < 0 || h<l)
                throw new IllegalArgumentException("invalid port range");

            return new int[] {l, h};
        }
    }

    /**
     * Returns true if the permission has specified zero
     * as its value (or lower bound) signifying the ephemeral range
     * <p>
     *  如果权限指定零为其值(或下限)表示临时范围,则返回true
     * 
     */
    private boolean includesEphemerals() {
        return portrange[0] == 0;
    }

    /**
     * Initialize the SocketPermission object. We don't do any DNS lookups
     * as this point, instead we hold off until the implies method is
     * called.
     * <p>
     *  初始化SocketPermission对象我们这里不做任何DNS查找,而是直到调用了implicit方法为止
     * 
     */
    private void init(String host, int mask) {
        // Set the integer mask that represents the actions

        if ((mask & ALL) != mask)
            throw new IllegalArgumentException("invalid actions mask");

        // always OR in RESOLVE if we allow any of the others
        this.mask = mask | RESOLVE;

        // Parse the host name.  A name has up to three components, the
        // hostname, a port number, or two numbers representing a port
        // range.   "www.sun.com:8080-9090" is a valid host name.

        // With IPv6 an address can be 2010:836B:4179::836B:4179
        // An IPv6 address needs to be enclose in []
        // For ex: [2010:836B:4179::836B:4179]:8080-9090
        // Refer to RFC 2732 for more information.

        int rb = 0 ;
        int start = 0, end = 0;
        int sep = -1;
        String hostport = host;
        if (host.charAt(0) == '[') {
            start = 1;
            rb = host.indexOf(']');
            if (rb != -1) {
                host = host.substring(start, rb);
            } else {
                throw new
                    IllegalArgumentException("invalid host/port: "+host);
            }
            sep = hostport.indexOf(':', rb+1);
        } else {
            start = 0;
            sep = host.indexOf(':', rb);
            end = sep;
            if (sep != -1) {
                host = host.substring(start, end);
            }
        }

        if (sep != -1) {
            String port = hostport.substring(sep+1);
            try {
                portrange = parsePort(port);
            } catch (Exception e) {
                throw new
                    IllegalArgumentException("invalid port range: "+port);
            }
        } else {
            portrange = new int[] { PORT_MIN, PORT_MAX };
        }

        hostname = host;

        // is this a domain wildcard specification
        if (host.lastIndexOf('*') > 0) {
            throw new
               IllegalArgumentException("invalid host wildcard specification");
        } else if (host.startsWith("*")) {
            wildcard = true;
            if (host.equals("*")) {
                cname = "";
            } else if (host.startsWith("*.")) {
                cname = host.substring(1).toLowerCase();
            } else {
              throw new
               IllegalArgumentException("invalid host wildcard specification");
            }
            return;
        } else {
            if (host.length() > 0) {
                // see if we are being initialized with an IP address.
                char ch = host.charAt(0);
                if (ch == ':' || Character.digit(ch, 16) != -1) {
                    byte ip[] = IPAddressUtil.textToNumericFormatV4(host);
                    if (ip == null) {
                        ip = IPAddressUtil.textToNumericFormatV6(host);
                    }
                    if (ip != null) {
                        try {
                            addresses =
                                new InetAddress[]
                                {InetAddress.getByAddress(ip) };
                            init_with_ip = true;
                        } catch (UnknownHostException uhe) {
                            // this shouldn't happen
                            invalid = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Convert an action string to an integer actions mask.
     *
     * <p>
     *  将操作字符串转换为整数操作掩码
     * 
     * 
     * @param action the action string
     * @return the action mask
     */
    private static int getMask(String action) {

        if (action == null) {
            throw new NullPointerException("action can't be null");
        }

        if (action.equals("")) {
            throw new IllegalArgumentException("action can't be empty");
        }

        int mask = NONE;

        // Use object identity comparison against known-interned strings for
        // performance benefit (these values are used heavily within the JDK).
        if (action == SecurityConstants.SOCKET_RESOLVE_ACTION) {
            return RESOLVE;
        } else if (action == SecurityConstants.SOCKET_CONNECT_ACTION) {
            return CONNECT;
        } else if (action == SecurityConstants.SOCKET_LISTEN_ACTION) {
            return LISTEN;
        } else if (action == SecurityConstants.SOCKET_ACCEPT_ACTION) {
            return ACCEPT;
        } else if (action == SecurityConstants.SOCKET_CONNECT_ACCEPT_ACTION) {
            return CONNECT|ACCEPT;
        }

        char[] a = action.toCharArray();

        int i = a.length - 1;
        if (i < 0)
            return mask;

        while (i != -1) {
            char c;

            // skip whitespace
            while ((i!=-1) && ((c = a[i]) == ' ' ||
                               c == '\r' ||
                               c == '\n' ||
                               c == '\f' ||
                               c == '\t'))
                i--;

            // check for the known strings
            int matchlen;

            if (i >= 6 && (a[i-6] == 'c' || a[i-6] == 'C') &&
                          (a[i-5] == 'o' || a[i-5] == 'O') &&
                          (a[i-4] == 'n' || a[i-4] == 'N') &&
                          (a[i-3] == 'n' || a[i-3] == 'N') &&
                          (a[i-2] == 'e' || a[i-2] == 'E') &&
                          (a[i-1] == 'c' || a[i-1] == 'C') &&
                          (a[i] == 't' || a[i] == 'T'))
            {
                matchlen = 7;
                mask |= CONNECT;

            } else if (i >= 6 && (a[i-6] == 'r' || a[i-6] == 'R') &&
                                 (a[i-5] == 'e' || a[i-5] == 'E') &&
                                 (a[i-4] == 's' || a[i-4] == 'S') &&
                                 (a[i-3] == 'o' || a[i-3] == 'O') &&
                                 (a[i-2] == 'l' || a[i-2] == 'L') &&
                                 (a[i-1] == 'v' || a[i-1] == 'V') &&
                                 (a[i] == 'e' || a[i] == 'E'))
            {
                matchlen = 7;
                mask |= RESOLVE;

            } else if (i >= 5 && (a[i-5] == 'l' || a[i-5] == 'L') &&
                                 (a[i-4] == 'i' || a[i-4] == 'I') &&
                                 (a[i-3] == 's' || a[i-3] == 'S') &&
                                 (a[i-2] == 't' || a[i-2] == 'T') &&
                                 (a[i-1] == 'e' || a[i-1] == 'E') &&
                                 (a[i] == 'n' || a[i] == 'N'))
            {
                matchlen = 6;
                mask |= LISTEN;

            } else if (i >= 5 && (a[i-5] == 'a' || a[i-5] == 'A') &&
                                 (a[i-4] == 'c' || a[i-4] == 'C') &&
                                 (a[i-3] == 'c' || a[i-3] == 'C') &&
                                 (a[i-2] == 'e' || a[i-2] == 'E') &&
                                 (a[i-1] == 'p' || a[i-1] == 'P') &&
                                 (a[i] == 't' || a[i] == 'T'))
            {
                matchlen = 6;
                mask |= ACCEPT;

            } else {
                // parse error
                throw new IllegalArgumentException(
                        "invalid permission: " + action);
            }

            // make sure we didn't just match the tail of a word
            // like "ackbarfaccept".  Also, skip to the comma.
            boolean seencomma = false;
            while (i >= matchlen && !seencomma) {
                switch(a[i-matchlen]) {
                case ',':
                    seencomma = true;
                    break;
                case ' ': case '\r': case '\n':
                case '\f': case '\t':
                    break;
                default:
                    throw new IllegalArgumentException(
                            "invalid permission: " + action);
                }
                i--;
            }

            // point i at the location of the comma minus one (or -1).
            i -= matchlen;
        }

        return mask;
    }

    private boolean isUntrusted()
        throws UnknownHostException
    {
        if (trusted) return false;
        if (invalid || untrusted) return true;
        try {
            if (!trustNameService && (defaultDeny ||
                sun.net.www.URLConnection.isProxiedHost(hostname))) {
                if (this.cname == null) {
                    this.getCanonName();
                }
                if (!match(cname, hostname)) {
                    // Last chance
                    if (!authorized(hostname, addresses[0].getAddress())) {
                        untrusted = true;
                        Debug debug = getDebug();
                        if (debug != null && Debug.isOn("failure")) {
                            debug.println("socket access restriction: proxied host " + "(" + addresses[0] + ")" + " does not match " + cname + " from reverse lookup");
                        }
                        return true;
                    }
                }
                trusted = true;
            }
        } catch (UnknownHostException uhe) {
            invalid = true;
            throw uhe;
        }
        return false;
    }

    /**
     * attempt to get the fully qualified domain name
     *
     * <p>
     *  尝试获取完全限定域名
     * 
     */
    void getCanonName()
        throws UnknownHostException
    {
        if (cname != null || invalid || untrusted) return;

        // attempt to get the canonical name

        try {
            // first get the IP addresses if we don't have them yet
            // this is because we need the IP address to then get
            // FQDN.
            if (addresses == null) {
                getIP();
            }

            // we have to do this check, otherwise we might not
            // get the fully qualified domain name
            if (init_with_ip) {
                cname = addresses[0].getHostName(false).toLowerCase();
            } else {
             cname = InetAddress.getByName(addresses[0].getHostAddress()).
                                              getHostName(false).toLowerCase();
            }
        } catch (UnknownHostException uhe) {
            invalid = true;
            throw uhe;
        }
    }

    private transient String cdomain, hdomain;

    private boolean match(String cname, String hname) {
        String a = cname.toLowerCase();
        String b = hname.toLowerCase();
        if (a.startsWith(b)  &&
            ((a.length() == b.length()) || (a.charAt(b.length()) == '.')))
            return true;
        if (cdomain == null) {
            cdomain = RegisteredDomain.getRegisteredDomain(a);
        }
        if (hdomain == null) {
            hdomain = RegisteredDomain.getRegisteredDomain(b);
        }

        return cdomain.length() != 0 && hdomain.length() != 0
                        && cdomain.equals(hdomain);
    }

    private boolean authorized(String cname, byte[] addr) {
        if (addr.length == 4)
            return authorizedIPv4(cname, addr);
        else if (addr.length == 16)
            return authorizedIPv6(cname, addr);
        else
            return false;
    }

    private boolean authorizedIPv4(String cname, byte[] addr) {
        String authHost = "";
        InetAddress auth;

        try {
            authHost = "auth." +
                        (addr[3] & 0xff) + "." + (addr[2] & 0xff) + "." +
                        (addr[1] & 0xff) + "." + (addr[0] & 0xff) +
                        ".in-addr.arpa";
            // Following check seems unnecessary
            // auth = InetAddress.getAllByName0(authHost, false)[0];
            authHost = hostname + '.' + authHost;
            auth = InetAddress.getAllByName0(authHost, false)[0];
            if (auth.equals(InetAddress.getByAddress(addr))) {
                return true;
            }
            Debug debug = getDebug();
            if (debug != null && Debug.isOn("failure")) {
                debug.println("socket access restriction: IP address of " + auth + " != " + InetAddress.getByAddress(addr));
            }
        } catch (UnknownHostException uhe) {
            Debug debug = getDebug();
            if (debug != null && Debug.isOn("failure")) {
                debug.println("socket access restriction: forward lookup failed for " + authHost);
            }
        }
        return false;
    }

    private boolean authorizedIPv6(String cname, byte[] addr) {
        String authHost = "";
        InetAddress auth;

        try {
            StringBuffer sb = new StringBuffer(39);

            for (int i = 15; i >= 0; i--) {
                sb.append(Integer.toHexString(((addr[i]) & 0x0f)));
                sb.append('.');
                sb.append(Integer.toHexString(((addr[i] >> 4) & 0x0f)));
                sb.append('.');
            }
            authHost = "auth." + sb.toString() + "IP6.ARPA";
            //auth = InetAddress.getAllByName0(authHost, false)[0];
            authHost = hostname + '.' + authHost;
            auth = InetAddress.getAllByName0(authHost, false)[0];
            if (auth.equals(InetAddress.getByAddress(addr)))
                return true;
            Debug debug = getDebug();
            if (debug != null && Debug.isOn("failure")) {
                debug.println("socket access restriction: IP address of " + auth + " != " + InetAddress.getByAddress(addr));
            }
        } catch (UnknownHostException uhe) {
            Debug debug = getDebug();
            if (debug != null && Debug.isOn("failure")) {
                debug.println("socket access restriction: forward lookup failed for " + authHost);
            }
        }
        return false;
    }


    /**
     * get IP addresses. Sets invalid to true if we can't get them.
     *
     * <p>
     *  获取IP地址如果我们无法获取IP地址,则将invalid设置为true
     * 
     */
    void getIP()
        throws UnknownHostException
    {
        if (addresses != null || wildcard || invalid) return;

        try {
            // now get all the IP addresses
            String host;
            if (getName().charAt(0) == '[') {
                // Literal IPv6 address
                host = getName().substring(1, getName().indexOf(']'));
            } else {
                int i = getName().indexOf(":");
                if (i == -1)
                    host = getName();
                else {
                    host = getName().substring(0,i);
                }
            }

            addresses =
                new InetAddress[] {InetAddress.getAllByName0(host, false)[0]};

        } catch (UnknownHostException uhe) {
            invalid = true;
            throw uhe;
        }  catch (IndexOutOfBoundsException iobe) {
            invalid = true;
            throw new UnknownHostException(getName());
        }
    }

    /**
     * Checks if this socket permission object "implies" the
     * specified permission.
     * <P>
     * More specifically, this method first ensures that all of the following
     * are true (and returns false if any of them are not):
     * <ul>
     * <li> <i>p</i> is an instanceof SocketPermission,
     * <li> <i>p</i>'s actions are a proper subset of this
     * object's actions, and
     * <li> <i>p</i>'s port range is included in this port range. Note:
     * port range is ignored when p only contains the action, 'resolve'.
     * </ul>
     *
     * Then {@code implies} checks each of the following, in order,
     * and for each returns true if the stated condition is true:
     * <ul>
     * <li> If this object was initialized with a single IP address and one of <i>p</i>'s
     * IP addresses is equal to this object's IP address.
     * <li>If this object is a wildcard domain (such as *.sun.com), and
     * <i>p</i>'s canonical name (the name without any preceding *)
     * ends with this object's canonical host name. For example, *.sun.com
     * implies *.eng.sun.com.
     * <li>If this object was not initialized with a single IP address, and one of this
     * object's IP addresses equals one of <i>p</i>'s IP addresses.
     * <li>If this canonical name equals <i>p</i>'s canonical name.
     * </ul>
     *
     * If none of the above are true, {@code implies} returns false.
     * <p>
     *  检查此套接字权限对象是否"暗示"指定的权限
     * <P>
     *  更具体地说,这个方法首先确保所有以下都是真的(如果任何一个不返回false则返回false)：
     * <ul>
     * <li> <i> p </i>是SocketPermission的实例,<li> <i> p </i>的操作是此对象操作的一个子集,<li> <i> p </i >的端口范围包含在此端口范围中注意：当p
     * 仅包含操作"resolve"时,将忽略端口范围,。
     * </ul>
     * 
     *  然后{@code implies}按顺序检查下列各项,如果所述条件为真,则每个返回true：
     * <ul>
     * <li>如果此对象已使用单个IP地址初始化,并且<i> p </i>的IP地址之一等于此对象的IP地址<li>如果此对象是通配符域(例如* suncom )和<i> p </i>的规范名称(没有任何前面
     * 的*的名称)以此对象的规范主机名结尾例如,* suncom表示* engsuncom <li>如果此对象未使用单个IP地址,并且此对象的IP地址之一等于<i> p </i>的IP地址之一<li>如果此规
     * 范名称等于<i> p </i>的规范名称。
     * </ul>
     * 
     *  如果上面没有一个是真的,{@code implies}返回false
     * 
     * 
     * @param p the permission to check against.
     *
     * @return true if the specified permission is implied by this object,
     * false if not.
     */
    public boolean implies(Permission p) {
        int i,j;

        if (!(p instanceof SocketPermission))
            return false;

        if (p == this)
            return true;

        SocketPermission that = (SocketPermission) p;

        return ((this.mask & that.mask) == that.mask) &&
                                        impliesIgnoreMask(that);
    }

    /**
     * Checks if the incoming Permission's action are a proper subset of
     * the this object's actions.
     * <P>
     * Check, in the following order:
     * <ul>
     * <li> Checks that "p" is an instanceof a SocketPermission
     * <li> Checks that "p"'s actions are a proper subset of the
     * current object's actions.
     * <li> Checks that "p"'s port range is included in this port range
     * <li> If this object was initialized with an IP address, checks that
     *      one of "p"'s IP addresses is equal to this object's IP address.
     * <li> If either object is a wildcard domain (i.e., "*.sun.com"),
     *      attempt to match based on the wildcard.
     * <li> If this object was not initialized with an IP address, attempt
     *      to find a match based on the IP addresses in both objects.
     * <li> Attempt to match on the canonical hostnames of both objects.
     * </ul>
     * <p>
     *  检查传入的权限的操作是否是此对象操作的正确子集
     * <P>
     *  按以下顺序检查：
     * <ul>
     * <li>检查"p"是SocketPermission的实例<li>检查"p"的操作是当前对象操作的正确子集<li>检查"p"的端口范围是否包含在此端口中范围<li>如果此对象使用IP地址初始化,请检查"
     * p"的IP地址之一是否等于此对象的IP地址<li>如果任一对象是通配符域(即"* suncom") ,尝试基于通配符进行匹配<li>如果此对象未使用IP地址初始化,请尝试根据两个对象中的IP地址查找匹配
     * <li>尝试在两个对象的规范主机名上进行匹配。
     * </ul>
     * 
     * @param that the incoming permission request
     *
     * @return true if "permission" is a proper subset of the current object,
     * false if not.
     */
    boolean impliesIgnoreMask(SocketPermission that) {
        int i,j;

        if ((that.mask & RESOLVE) != that.mask) {

            // check simple port range
            if ((that.portrange[0] < this.portrange[0]) ||
                    (that.portrange[1] > this.portrange[1])) {

                // if either includes the ephemeral range, do full check
                if (this.includesEphemerals() || that.includesEphemerals()) {
                    if (!inRange(this.portrange[0], this.portrange[1],
                                     that.portrange[0], that.portrange[1]))
                    {
                                return false;
                    }
                } else {
                    return false;
                }
            }
        }

        // allow a "*" wildcard to always match anything
        if (this.wildcard && "".equals(this.cname))
            return true;

        // return if either one of these NetPerm objects are invalid...
        if (this.invalid || that.invalid) {
            return compareHostnames(that);
        }

        try {
            if (this.init_with_ip) { // we only check IP addresses
                if (that.wildcard)
                    return false;

                if (that.init_with_ip) {
                    return (this.addresses[0].equals(that.addresses[0]));
                } else {
                    if (that.addresses == null) {
                        that.getIP();
                    }
                    for (i=0; i < that.addresses.length; i++) {
                        if (this.addresses[0].equals(that.addresses[i]))
                            return true;
                    }
                }
                // since "this" was initialized with an IP address, we
                // don't check any other cases
                return false;
            }

            // check and see if we have any wildcards...
            if (this.wildcard || that.wildcard) {
                // if they are both wildcards, return true iff
                // that's cname ends with this cname (i.e., *.sun.com
                // implies *.eng.sun.com)
                if (this.wildcard && that.wildcard)
                    return (that.cname.endsWith(this.cname));

                // a non-wildcard can't imply a wildcard
                if (that.wildcard)
                    return false;

                // this is a wildcard, lets see if that's cname ends with
                // it...
                if (that.cname == null) {
                    that.getCanonName();
                }
                return (that.cname.endsWith(this.cname));
            }

            // comapare IP addresses
            if (this.addresses == null) {
                this.getIP();
            }

            if (that.addresses == null) {
                that.getIP();
            }

            if (!(that.init_with_ip && this.isUntrusted())) {
                for (j = 0; j < this.addresses.length; j++) {
                    for (i=0; i < that.addresses.length; i++) {
                        if (this.addresses[j].equals(that.addresses[i]))
                            return true;
                    }
                }

                // XXX: if all else fails, compare hostnames?
                // Do we really want this?
                if (this.cname == null) {
                    this.getCanonName();
                }

                if (that.cname == null) {
                    that.getCanonName();
                }

                return (this.cname.equalsIgnoreCase(that.cname));
            }

        } catch (UnknownHostException uhe) {
            return compareHostnames(that);
        }

        // make sure the first thing that is done here is to return
        // false. If not, uncomment the return false in the above catch.

        return false;
    }

    private boolean compareHostnames(SocketPermission that) {
        // we see if the original names/IPs passed in were equal.

        String thisHost = hostname;
        String thatHost = that.hostname;

        if (thisHost == null) {
            return false;
        } else if (this.wildcard) {
            final int cnameLength = this.cname.length();
            return thatHost.regionMatches(true,
                                          (thatHost.length() - cnameLength),
                                          this.cname, 0, cnameLength);
        } else {
            return thisHost.equalsIgnoreCase(thatHost);
        }
    }

    /**
     * Checks two SocketPermission objects for equality.
     * <P>
     * <p>
     *  检查两个SocketPermission对象是否相等
     * <P>
     * 
     * @param obj the object to test for equality with this object.
     *
     * @return true if <i>obj</i> is a SocketPermission, and has the
     *  same hostname, port range, and actions as this
     *  SocketPermission object. However, port range will be ignored
     *  in the comparison if <i>obj</i> only contains the action, 'resolve'.
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (! (obj instanceof SocketPermission))
            return false;

        SocketPermission that = (SocketPermission) obj;

        //this is (overly?) complex!!!

        // check the mask first
        if (this.mask != that.mask) return false;

        if ((that.mask & RESOLVE) != that.mask) {
            // now check the port range...
            if ((this.portrange[0] != that.portrange[0]) ||
                (this.portrange[1] != that.portrange[1])) {
                return false;
            }
        }

        // short cut. This catches:
        //  "crypto" equal to "crypto", or
        // "1.2.3.4" equal to "1.2.3.4.", or
        //  "*.edu" equal to "*.edu", but it
        //  does not catch "crypto" equal to
        // "crypto.eng.sun.com".

        if (this.getName().equalsIgnoreCase(that.getName())) {
            return true;
        }

        // we now attempt to get the Canonical (FQDN) name and
        // compare that. If this fails, about all we can do is return
        // false.

        try {
            this.getCanonName();
            that.getCanonName();
        } catch (UnknownHostException uhe) {
            return false;
        }

        if (this.invalid || that.invalid)
            return false;

        if (this.cname != null) {
            return this.cname.equalsIgnoreCase(that.cname);
        }

        return false;
    }

    /**
     * Returns the hash code value for this object.
     *
     * <p>
     *  返回此对象的哈希码值
     * 
     * 
     * @return a hash code value for this object.
     */

    public int hashCode() {
        /*
         * If this SocketPermission was initialized with an IP address
         * or a wildcard, use getName().hashCode(), otherwise use
         * the hashCode() of the host name returned from
         * java.net.InetAddress.getHostName method.
         * <p>
         * 如果此SocketPermission使用IP地址或通配符初始化,请使用getName()hashCode(),否则使用从javanetInetAddressgetHostName方法返回的主机名的ha
         * shCode()。
         * 
         */

        if (init_with_ip || wildcard) {
            return this.getName().hashCode();
        }

        try {
            getCanonName();
        } catch (UnknownHostException uhe) {

        }

        if (invalid || cname == null)
            return this.getName().hashCode();
        else
            return this.cname.hashCode();
    }

    /**
     * Return the current action mask.
     *
     * <p>
     *  返回当前操作掩码
     * 
     * 
     * @return the actions mask.
     */

    int getMask() {
        return mask;
    }

    /**
     * Returns the "canonical string representation" of the actions in the
     * specified mask.
     * Always returns present actions in the following order:
     * connect, listen, accept, resolve.
     *
     * <p>
     *  返回指定掩码中的操作的"规范字符串表示"始终按以下顺序返回当前操作：connect,listen,accept,resolve
     * 
     * 
     * @param mask a specific integer action mask to translate into a string
     * @return the canonical string representation of the actions
     */
    private static String getActions(int mask)
    {
        StringBuilder sb = new StringBuilder();
        boolean comma = false;

        if ((mask & CONNECT) == CONNECT) {
            comma = true;
            sb.append("connect");
        }

        if ((mask & LISTEN) == LISTEN) {
            if (comma) sb.append(',');
            else comma = true;
            sb.append("listen");
        }

        if ((mask & ACCEPT) == ACCEPT) {
            if (comma) sb.append(',');
            else comma = true;
            sb.append("accept");
        }


        if ((mask & RESOLVE) == RESOLVE) {
            if (comma) sb.append(',');
            else comma = true;
            sb.append("resolve");
        }

        return sb.toString();
    }

    /**
     * Returns the canonical string representation of the actions.
     * Always returns present actions in the following order:
     * connect, listen, accept, resolve.
     *
     * <p>
     *  返回操作的规范字符串表示始终按以下顺序返回当前操作：connect,listen,accept,resolve
     * 
     * 
     * @return the canonical string representation of the actions.
     */
    public String getActions()
    {
        if (actions == null)
            actions = getActions(this.mask);

        return actions;
    }

    /**
     * Returns a new PermissionCollection object for storing SocketPermission
     * objects.
     * <p>
     * SocketPermission objects must be stored in a manner that allows them
     * to be inserted into the collection in any order, but that also enables the
     * PermissionCollection {@code implies}
     * method to be implemented in an efficient (and consistent) manner.
     *
     * <p>
     *  返回一个新的PermissionCollection对象用于存储SocketPermission对象
     * <p>
     * SocketPermission对象必须以允许以任何顺序插入到集合中的方式存储,但这也使得PermissionCollection {@code implies}方法能够以高效(且一致)的方式实现
     * 
     * 
     * @return a new PermissionCollection object suitable for storing SocketPermissions.
     */

    public PermissionCollection newPermissionCollection() {
        return new SocketPermissionCollection();
    }

    /**
     * WriteObject is called to save the state of the SocketPermission
     * to a stream. The actions are serialized, and the superclass
     * takes care of the name.
     * <p>
     *  调用WriteObject来将SocketPermission的状态保存到流操作是序列化的,超类负责处理名称
     * 
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s)
        throws IOException
    {
        // Write out the actions. The superclass takes care of the name
        // call getActions to make sure actions field is initialized
        if (actions == null)
            getActions();
        s.defaultWriteObject();
    }

    /**
     * readObject is called to restore the state of the SocketPermission from
     * a stream.
     * <p>
     *  readObject被调用以从流恢复SocketPermission的状态
     * 
     */
    private synchronized void readObject(java.io.ObjectInputStream s)
         throws IOException, ClassNotFoundException
    {
        // Read in the action, then initialize the rest
        s.defaultReadObject();
        init(getName(),getMask(actions));
    }

    /**
     * Check the system/security property for the ephemeral port range
     * for this system. The suffix is either "high" or "low"
     * <p>
     *  检查此系统的临时端口范围的系统/安全属性后缀为"高"或"低"
     * 
     */
    private static int initEphemeralPorts(String suffix, int defval) {
        return AccessController.doPrivileged(
            new PrivilegedAction<Integer>(){
                public Integer run() {
                    int val = Integer.getInteger(
                            "jdk.net.ephemeralPortRange."+suffix, -1
                    );
                    if (val != -1) {
                        return val;
                    } else {
                        return suffix.equals("low") ?
                            PortConfig.getLower() : PortConfig.getUpper();
                    }
                }
            }
        );
    }

    /**
     * Check if the target range is within the policy range
     * together with the ephemeral range for this platform
     * (if policy includes ephemeral range)
     * <p>
     *  检查目标范围是否在此平台的临时范围(如果策略包括临时范围)以及策略范围内,
     * 
     */
    private static boolean inRange(
        int policyLow, int policyHigh, int targetLow, int targetHigh
    )
    {
        final int ephemeralLow = EphemeralRange.low;
        final int ephemeralHigh = EphemeralRange.high;

        if (targetLow == 0) {
            // check policy includes ephemeral range
            if (!inRange(policyLow, policyHigh, ephemeralLow, ephemeralHigh)) {
                return false;
            }
            if (targetHigh == 0) {
                // nothing left to do
                return true;
            }
            // continue check with first real port number
            targetLow = 1;
        }

        if (policyLow == 0 && policyHigh == 0) {
            // ephemeral range only
            return targetLow >= ephemeralLow && targetHigh <= ephemeralHigh;
        }

        if (policyLow != 0) {
            // simple check of policy only
            return targetLow >= policyLow && targetHigh <= policyHigh;
        }

        // policyLow == 0 which means possibly two ranges to check

        // first check if policy and ephem range overlap/contiguous

        if (policyHigh >= ephemeralLow - 1) {
            return targetHigh <= ephemeralHigh;
        }

        // policy and ephem range do not overlap

        // target range must lie entirely inside policy range or eph range

        return  (targetLow <= policyHigh && targetHigh <= policyHigh) ||
                (targetLow >= ephemeralLow && targetHigh <= ephemeralHigh);
    }
    /*
    public String toString()
    {
        StringBuffer s = new StringBuffer(super.toString() + "\n" +
            "cname = " + cname + "\n" +
            "wildcard = " + wildcard + "\n" +
            "invalid = " + invalid + "\n" +
            "portrange = " + portrange[0] + "," + portrange[1] + "\n");
        if (addresses != null) for (int i=0; i<addresses.length; i++) {
            s.append( addresses[i].getHostAddress());
            s.append("\n");
        } else {
            s.append("(no addresses)\n");
        }

        return s.toString();
    }

    public static void main(String args[]) throws Exception {
        SocketPermission this_ = new SocketPermission(args[0], "connect");
        SocketPermission that_ = new SocketPermission(args[1], "connect");
        System.out.println("-----\n");
        System.out.println("this.implies(that) = " + this_.implies(that_));
        System.out.println("-----\n");
        System.out.println("this = "+this_);
        System.out.println("-----\n");
        System.out.println("that = "+that_);
        System.out.println("-----\n");

        SocketPermissionCollection nps = new SocketPermissionCollection();
        nps.add(this_);
        nps.add(new SocketPermission("www-leland.stanford.edu","connect"));
        nps.add(new SocketPermission("www-sun.com","connect"));
        System.out.println("nps.implies(that) = " + nps.implies(that_));
        System.out.println("-----\n");
    }
    /* <p>
    /* public String toString(){StringBuffer s = new StringBuffer(supertoString()+"\n"+"cname ="+ cname +"\n"+"wildcard ="+ wildcard +"\n"+" ="+ invalid +"\n"+"portrange ="+ portrange [0] +","+ portrange [1] +"\n if(addresses！= null)for(int i = 0; i <addresseslength; i ++){sappend(addresses [i] getHostAddress()); sappend("\n"); } else {sappend("(no addresses)\n"); }}。
    /* 
    /*  return stoString(); }}
    /* 
    /* public static void main(String args [])throws Exception {SocketPermission this_ = new SocketPermission(args [0],"connect"); SocketPermission that_ = new SocketPermission(args [1],"connect"); Systemoutprintln("-----\n"); Systemoutprintln("thisimplies(that)="+ this_implies(that_)); Systemoutprintln("-----\n"); Systemoutprintln("this ="+ this_); Systemoutprintln("-----\n"); Systemoutprintln("that ="+ that_); Systemoutprintln("-----\n");。
    /* 
    /*  SocketPermissionCollection nps = new SocketPermissionCollection(); npsadd(this_); npsadd(new SocketP
    /* ermission("www-lelandstanfordedu","connect")); npsadd(new SocketPermission("www-suncom","connect")); 
    /* Systemoutprintln("npsimplies(that)="+ npsimplies(that_)); Systemoutprintln("-----\n"); }}。
    /* 
    */
}

/**

if (init'd with IP, key is IP as string)
if wildcard, its the wild card
else its the cname?

 *
 * <p>
 * if(init'd与IP,key是IP作为字符串)如果通配符,其通配符否则其cname?
 * 
 * 
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 *
 *
 * @author Roland Schemers
 *
 * @serial include
 */

final class SocketPermissionCollection extends PermissionCollection
    implements Serializable
{
    // Not serialized; see serialization section at end of class
    private transient List<SocketPermission> perms;

    /**
     * Create an empty SocketPermissions object.
     *
     * <p>
     *  创建一个空的SocketPermissions对象
     * 
     */

    public SocketPermissionCollection() {
        perms = new ArrayList<SocketPermission>();
    }

    /**
     * Adds a permission to the SocketPermissions. The key for the hash is
     * the name in the case of wildcards, or all the IP addresses.
     *
     * <p>
     *  向SocketPermissions添加权限哈希的键是通配符名称或所有IP地址的名称
     * 
     * 
     * @param permission the Permission object to add.
     *
     * @exception IllegalArgumentException - if the permission is not a
     *                                       SocketPermission
     *
     * @exception SecurityException - if this SocketPermissionCollection object
     *                                has been marked readonly
     */
    public void add(Permission permission) {
        if (! (permission instanceof SocketPermission))
            throw new IllegalArgumentException("invalid permission: "+
                                               permission);
        if (isReadOnly())
            throw new SecurityException(
                "attempt to add a Permission to a readonly PermissionCollection");

        // optimization to ensure perms most likely to be tested
        // show up early (4301064)
        synchronized (this) {
            perms.add(0, (SocketPermission)permission);
        }
    }

    /**
     * Check and see if this collection of permissions implies the permissions
     * expressed in "permission".
     *
     * <p>
     *  检查并查看此权限集合是否意味着在"权限"中表示的权限
     * 
     * 
     * @param permission the Permission object to compare
     *
     * @return true if "permission" is a proper subset of a permission in
     * the collection, false if not.
     */

    public boolean implies(Permission permission)
    {
        if (! (permission instanceof SocketPermission))
                return false;

        SocketPermission np = (SocketPermission) permission;

        int desired = np.getMask();
        int effective = 0;
        int needed = desired;

        synchronized (this) {
            int len = perms.size();
            //System.out.println("implies "+np);
            for (int i = 0; i < len; i++) {
                SocketPermission x = perms.get(i);
                //System.out.println("  trying "+x);
                if (((needed & x.getMask()) != 0) && x.impliesIgnoreMask(np)) {
                    effective |=  x.getMask();
                    if ((effective & desired) == desired)
                        return true;
                    needed = (desired ^ effective);
                }
            }
        }
        return false;
    }

    /**
     * Returns an enumeration of all the SocketPermission objects in the
     * container.
     *
     * <p>
     *  返回容器中所有SocketPermission对象的枚举
     * 
     * 
     * @return an enumeration of all the SocketPermission objects.
     */

    @SuppressWarnings("unchecked")
    public Enumeration<Permission> elements() {
        // Convert Iterator into Enumeration
        synchronized (this) {
            return Collections.enumeration((List<Permission>)(List)perms);
        }
    }

    private static final long serialVersionUID = 2787186408602843674L;

    // Need to maintain serialization interoperability with earlier releases,
    // which had the serializable field:

    //
    // The SocketPermissions for this set.
    // @serial
    //
    // private Vector permissions;

    /**
    /* <p>
    /* 
     * @serialField permissions java.util.Vector
     *     A list of the SocketPermissions for this set.
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("permissions", Vector.class),
    };

    /**
    /* <p>
    /* 
     * @serialData "permissions" field (a Vector containing the SocketPermissions).
     */
    /*
     * Writes the contents of the perms field out as a Vector for
     * serialization compatibility with earlier releases.
     * <p>
     *  将perms字段的内容作为Vector与先前版本的序列化兼容性写入
     * 
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Don't call out.defaultWriteObject()

        // Write out Vector
        Vector<SocketPermission> permissions = new Vector<>(perms.size());

        synchronized (this) {
            permissions.addAll(perms);
        }

        ObjectOutputStream.PutField pfields = out.putFields();
        pfields.put("permissions", permissions);
        out.writeFields();
    }

    /*
     * Reads in a Vector of SocketPermissions and saves them in the perms field.
     * <p>
     *  读取SocketPermissions的向量并将其保存在perms字段中
     */
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Don't call in.defaultReadObject()

        // Read in serialized fields
        ObjectInputStream.GetField gfields = in.readFields();

        // Get the one we want
        @SuppressWarnings("unchecked")
        Vector<SocketPermission> permissions = (Vector<SocketPermission>)gfields.get("permissions", null);
        perms = new ArrayList<SocketPermission>(permissions.size());
        perms.addAll(permissions);
    }
}
