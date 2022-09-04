package org.itsallcode.junit.sysextensions.security;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

public class ExitGuardSecurityManager extends SecurityManager
{
    private boolean trapExit = false;
    private final SecurityManager delegate;

    public ExitGuardSecurityManager(final SecurityManager delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public synchronized void checkExit(final int status)
    {
        if (this.trapExit)
        {
            this.trapExit = false;
            throw new ExitTrapException(this.getClass().getSimpleName() + " intercepted a System.exit(" + status + ").",
                    status);
        }
    }

    @Override
    public void checkPermission(final Permission perm)
    {
        // intentionally empty
    }

    /**
     * Switch trapping {@link System#exit(int)} calls on or off.
     *
     * @param trapExit
     *            set to <code>true</code> if the
     *            {@link ExitGuardSecurityManager} should trap exit calls
     */
    public void trapExit(final boolean trapExit)
    {
        this.trapExit = trapExit;
    }

    private boolean hasDelegate()
    {
        return this.delegate != null;
    }

    @Override
    public void checkAccept(final String host, final int port)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkAccept(host, port);
        }
    }

    @Override
    public void checkAccess(final Thread thread)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkAccess(thread);
        }
    }

    @Override
    public void checkAccess(final ThreadGroup group)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkAccess(group);
        }
    }

    @Override
    public void checkConnect(final String host, final int port, final Object context)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkConnect(host, port, context);
        }
    }

    @Override
    public void checkConnect(final String host, final int port)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkConnect(host, port);
        }
    }

    @Override
    public void checkCreateClassLoader()
    {
        if (this.hasDelegate())
        {
            this.delegate.checkCreateClassLoader();
        }
    }

    @Override
    public void checkDelete(final String file)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkDelete(file);
        }
    }

    @Override
    public void checkExec(final String cmd)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkExec(cmd);
        }
    }

    @Override
    public void checkLink(final String lib)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkLink(lib);
        }
    }

    @Override
    public void checkListen(final int port)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkListen(port);
        }
    }

    @Override
    public void checkMulticast(final InetAddress maddr)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkMulticast(maddr);
        }
    }

    @Override
    public void checkPackageAccess(final String pkg)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkPackageAccess(pkg);
        }
    }

    @Override
    public void checkPackageDefinition(final String pkg)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkPackageDefinition(pkg);
        }
    }

    @Override
    public void checkPermission(final Permission perm, final Object context)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkPermission(perm, context);
        }
    }

    @Override
    public void checkPrintJobAccess()
    {
        if (this.hasDelegate())
        {
            this.delegate.checkPrintJobAccess();
        }
    }

    @Override
    public void checkPropertiesAccess()
    {
        if (this.hasDelegate())
        {
            this.delegate.checkPropertiesAccess();
        }
    }

    @Override
    public void checkPropertyAccess(final String key)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkPropertyAccess(key);
        }
    }

    @Override
    public void checkRead(final FileDescriptor fd)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkRead(fd);
        }
    }

    @Override
    public void checkRead(final String file, final Object context)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkRead(file, context);
        }
    }

    @Override
    public void checkRead(final String file)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkRead(file);
        }
    }

    @Override
    public void checkSecurityAccess(final String target)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkSecurityAccess(target);
        }
    }

    @Override
    public void checkSetFactory()
    {
        if (this.hasDelegate())
        {
            this.delegate.checkSetFactory();
        }
    }

    @Override
    public void checkWrite(final FileDescriptor fileDescriptor)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkWrite(fileDescriptor);
        }
    }

    @Override
    public void checkWrite(final String file)
    {
        if (this.hasDelegate())
        {
            this.delegate.checkWrite(file);
        }
    }
}