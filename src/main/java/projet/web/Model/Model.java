package projet.web.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Njanah
 */
public class Model{
    Helper con=new Helper();
    //getters
    public int add(){
        return 0;
    }
    public String getters(String name)
    {
            String temp= "get" + name.substring(0,1).toUpperCase() + name.substring(1);
            return temp;
    }
    //getAttribut
    String[] getAttribut(Object ob) throws Exception
    {
            Class cl=ob.getClass();
            Field[] fl=cl.getDeclaredFields();
            String[] rep=new String[fl.length];
            String[] attName=new String[fl.length];
            String[] methodName=new String[fl.length];
            Method[] allMethod=new Method[fl.length];
            Object[] val=new Object[fl.length];

            for(int i=0;i<fl.length;i++)
            {                               
                    attName[i]=fl[i].getName();
                    methodName[i]=getters(attName[i]);
                    Method meth=cl.getMethod(methodName[i], null);
                    val[i]= meth.invoke(ob, null);
                    rep[i]=val[i].toString();
                    System.out.println(val[i].toString());
            }
            return rep;
    }
    public String getNow(Connection con) throws Exception
    {
    	String req="SELECT NOW()";
    	
    	String val=(String) getOne(req,"String",con);
    	return val;
    }
    public int nextVal(String table,Connection c) throws Exception
    {
    	String req="SELECT COUNT(*) FROM "+table;
    	int val=(int) this.getOne(req, "int", c);
    	return val+1;
    }
    
    public String inserer(String nomTable,String incr, Object ob) throws Exception
	{
		String[] attribut=getAttribut(ob);
		Field[] fl=ob.getClass().getDeclaredFields();
		String mysql="INSERT INTO "+nomTable+" (";
		int first=0;
		for(int i=0;i<attribut.length;i++)
		{
			mysql=mysql+fl[i].getName();
			if(i<attribut.length-1)
			{
				mysql=mysql+",";
			}
		}
		mysql=mysql+") VALUES (";
		if(incr!=null)
		{
			mysql=mysql+incr+",";
			first=1;
		}
		for(int i=first;i<attribut.length;i++)
		{
			mysql=mysql+"'"+attribut[i]+"'";
			if(i<attribut.length-1)
			{
				mysql=mysql+",";
			}
		}
		mysql=mysql+")";
		System.out.println(mysql);
		return mysql;
		//state.execute("COMMIT");
	}
    public void Insert(Connection c,String requete)throws Exception{
    	Statement state= c.createStatement();
		state.execute(requete);
    }
    public void inserer(String nomTable,String incr, Object ob,Connection connection) throws Exception
	{
		String[] attribut=getAttribut(ob);
		Field[] fl=ob.getClass().getDeclaredFields();
		String mysql="INSERT INTO "+nomTable+" (";
		int first=0;
		for(int i=0;i<attribut.length;i++)
		{
			mysql=mysql+fl[i].getName();
			if(i<attribut.length-1)
			{
				mysql=mysql+",";
			}
		}
		mysql=mysql+") VALUES (";
		if(incr!=null)
		{
			mysql=mysql+incr+",";
			first=1;
		}
		for(int i=first;i<attribut.length;i++)
		{
			mysql=mysql+"'"+attribut[i]+"'";
			if(i<attribut.length-1)
			{
				mysql=mysql+",";
			}
		}
		mysql=mysql+")";
		System.out.println(mysql);
		Statement state= connection.createStatement();
		state.execute(mysql);
		//state.execute("COMMIT");
	}

	public Vector resultRequest(String req,Connection connect)throws Exception
	{
		Vector valiny = new Vector();
		Statement state= connect.createStatement();
		System.out.print(req);
		ResultSet result=state.executeQuery(req);
		// On recupere le metaData
		ResultSetMetaData resultMeta=result.getMetaData();
		while(result.next()){
			for(int i=1;i<=resultMeta.getColumnCount();i++){
//				System.out.println("columnTypeName="+resultMeta.getColumnTypeName(i));
				if (resultMeta.getColumnTypeName(i).compareTo("int4")==0) {
					valiny.add(result.getInt(i));
//                    System.out.println(resultMeta.getColumnName(i)+"==INT");                        
				}
				if (resultMeta.getColumnTypeName(i).compareTo("float8")==0) {
					valiny.add(result.getDouble(i));
//                    System.out.println(resultMeta.getColumnName(i)+"==DOUBLE");
				}
				if(resultMeta.getColumnTypeName(i).compareTo("varchar")==0 || resultMeta.getColumnTypeName(i).compareTo("timestamp")==0)
				{
					valiny.add(result.getString(i));
//                    System.out.println(resultMeta.getColumnName(i)+"==STRING");
				}
			}
		}
		result.close();
		state.close();
		return valiny;
	}
	public Object[] vectToOb(Vector vect,Object ob) throws Exception
	{
		int len=ob.getClass().getDeclaredFields().length;
		Field[] fl=ob.getClass().getDeclaredFields();
		Class[] attClass=new Class[len];

		for (int i=0;i<fl.length ;i++ ) 
		{
			attClass[i]=fl[i].getType();	
		}
		Constructor constr=ob.getClass().getConstructor(attClass);
		Object[] val=new Object[vect.size()/len];
		if(len>0)
		{
			int count=0;
			val=new Object[vect.size()/len];
			for (int i=0;i<vect.size() ;i+=len) 
			{
				Object[] arg=new Object[len];
				for(int j=0;j<len;j++)
				{
					arg[j]=vect.get(i+j);
				}
				val[count]=constr.newInstance(arg);
				count++;		
			}
		}
		return val;
	}
	public Object[] getResult(String sql,Object ob,Connection connect) throws Exception
	{
		Vector vect=resultRequest(sql,connect);
		Object[] val=vectToOb(vect,ob);
		return val;
	}
	
        
	public void modifier(String nomTable, Object ob,Connection connect) throws Exception
	{
		Class cl=ob.getClass();
		Field[] fl=cl.getDeclaredFields();
		String[] attribut=getAttribut(ob);
		String mysql="UPDATE "+nomTable+" SET ";
		int first=1;
		for(int i=first;i<attribut.length;i++)
		{
			mysql=mysql+fl[i].getName()+"='"+attribut[i]+"'";
			if(i<attribut.length-1)
			{
				mysql=mysql+",";
			}
		}
		mysql=mysql+" WHERE "+fl[0].getName()+"="+attribut[0];
		Statement state= connect.createStatement();
		state.execute(mysql);
		//state.execute("COMMIT");
	}
        public Object getOne(String req,String type_retour,Connection connect)throws Exception
        {
            Object val=new Object();
            Statement state= connect.createStatement();
            ResultSet result=state.executeQuery(req);
            ResultSetMetaData resultMeta=result.getMetaData();
            while(result.next())
            {
                if(type_retour.compareTo("int")==0)
                {
                    val=result.getInt(1);
                }
                if(type_retour.compareTo("double")==0)
                {
                    val=result.getDouble(1);
                }
                if(type_retour.compareTo("String")==0)
                {
                    val=result.getString(1);
                }
            }
            result.close();
            state.close();
            return val;
        }
       }
