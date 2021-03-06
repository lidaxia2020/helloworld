/***** Lobxxx Translate Finished ******/
package org.omg.PortableServer;


/**
* org/omg/PortableServer/CurrentOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u45/3627/corba/src/share/classes/org/omg/PortableServer/poa.idl
* Thursday, April 30, 2015 12:42:10 PM PDT
*/


/**
	 * The PortableServer::Current interface, derived from 
	 * CORBA::Current, provides method implementations with 
	 * access to the identity of the object on which the 
	 * method was invoked. The Current interface is provided 
	 * to support servants that implement multiple objects, 
	 * but can be used within the context of POA-dispatched 
	 * method invocations on any servant. To provide location 
	 * transparency, ORBs are required to support use of 
	 * Current in the context of both locally and remotely 
	 * invoked operations. An instance of Current can be 
	 * obtained by the application by issuing the 
	 * CORBA::ORB::resolve_initial_references("POACurrent") 
	 * operation. Thereafter, it can be used within the 
	 * context of a method dispatched by the POA to obtain 
	 * the POA and ObjectId that identify the object on 
	 * which that operation was invoked.
	 * <p>
	 *  PortableServer :: Current接口派生自CORBA :: Current,提供了访问被调用方法的对象的标识的方法实现。
	 *  Current接口用于支持实现多个对象的服务,但可以在任何服务方上调用POA方法调用的上下文中使用。为了提供位置透明度,需要ORB在本地和远程调用操作的上下文中支持使用Current。
	 * 通过发出CORBA :: ORB :: resolve_initial_references("POACurrent")操作,应用程序可以获取Current的实例。
	 * 此后,它可以在POA调度的方法的上下文中使用,以获得标识调用该操作的对象的POA和ObjectId。
	 * 
	 */
public interface CurrentOperations  extends org.omg.CORBA.CurrentOperations
{

  /**
	 * Returns reference to the POA implementing the 
	 * object in whose context it is called. 
	 *
	 * <p>
	 *  返回对实现在其上下文中被调用的对象的POA的引用。
	 * 
	 * 
	 * @return The poa implementing the object
	 * 
	 * @exception NoContext is raised when the operation is
	 *            outside the context of a POA-dispatched 
	 *            operation
	 */
  org.omg.PortableServer.POA get_POA () throws org.omg.PortableServer.CurrentPackage.NoContext;

  /** 
	 * Returns the ObjectId identifying the object in 
	 * whose context it is called. 
	 *
	 * <p>
	 *  返回ObjectId标识在其上下文中被调用的对象。
	 * 
	 * @return the ObjectId of the object
	 *
	 * @exception NoContext is raised when the operation
	 * is called outside the context of a POA-dispatched 
	 * operation.
	 */
  byte[] get_object_id () throws org.omg.PortableServer.CurrentPackage.NoContext;
} // interface CurrentOperations
