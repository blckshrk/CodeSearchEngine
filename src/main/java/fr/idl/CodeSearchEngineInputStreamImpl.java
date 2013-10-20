package main.java.fr.idl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class CodeSearchEngineInputStreamImpl
		implements
			CodeSearchEngineInputStream {

	@Override
	public Type findType(String typeName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Type> findSubTypesOf(String typeName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Field> findFieldsTypedWith(String typeName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findAllReadAccessesOf(Field field, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findAllWriteAccessesOf(Field field, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Alexandre Bonhomme
	 */
	@Override
	public List<Method> findMethodsOf(String typeName, InputStream data) {
		ArrayList<Method> listMethod = new ArrayList<>();

		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		try {
			XMLStreamReader xmlsr = xmlif.createXMLStreamReader(data);

			int eventType;
			while (xmlsr.hasNext()) {
				eventType = xmlsr.next();

				switch (eventType) {
					case XMLEvent.START_ELEMENT :
						if (!xmlsr.getName().equals(typeName)) {
							continue;
						}

						// TODO faire une methode pour ça
						while (xmlsr.hasNext()) {
							eventType = xmlsr.next();

							if (eventType != XMLEvent.START_ELEMENT) {
								continue;
							}

							if (!xmlsr.getName().equals("function")) {
								continue;
							}

							MethodImpl method = new MethodImpl();
							while (xmlsr.hasNext()) {
								eventType = xmlsr.next();

								if (eventType != XMLEvent.START_ELEMENT) {
									continue;
								}
							}
						}

						break;
					default :
						break;
				}
			}

		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}

		return listMethod;
	}

	/**
	 * @author Julien Duribreux
	 */
	@Override
	public List<Method> findMethodsReturning(String typeName, InputStream data) {
		List<Method> listMethod = new ArrayList<Method>();
		boolean inMethod = false;
		boolean inType = false;
		boolean inParameter = false;
		boolean inBlock = false;
		String methodName = "";
		String outputType = "";

		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		try {
			XMLStreamReader xmlsr = xmlif.createXMLStreamReader(data);
			while (xmlsr.hasNext()) {
				int eventType = xmlsr.next();
				// Search function beacon
				switch (eventType) {
					case XMLEvent.START_ELEMENT :
						if (xmlsr.getLocalName().equals("function")) {
							inMethod = true;
						} else if (xmlsr.getLocalName().equals("type")) {
							inType = true;
						} else if (xmlsr.getLocalName()
								.equals("parameter_list")) {
							inParameter = true;
						} else if (xmlsr.getLocalName().equals("block")) {
							inBlock = true;
						}
						break;
					case XMLEvent.CHARACTERS :
						if (inMethod && inType && !inParameter && !inBlock) {
							methodName = xmlsr.getText();
							System.out.println("Methode : " + methodName);
						}
						if (inType) {
							outputType = xmlsr.getText();
							System.out.println("OuputType : " + outputType);
						}
						break;
					case XMLEvent.END_ELEMENT :
						if (xmlsr.getLocalName().equals("function")) {
							// Reset
							inMethod = false;
							methodName = "";
							outputType = "";
						} else if (xmlsr.getLocalName().equals("type")) {
							inType = false;
						} else if (xmlsr.getLocalName()
								.equals("parameter_list")) {
							inParameter = false;
						} else if (xmlsr.getLocalName().equals("block")) {
							inBlock = false;
						}
						break;
					default :
						break;
				}
			}

		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}

		return listMethod;
	}
	@Override
	public List<Method> findMethodsTakingAsParameter(String typeName,
			InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsCalled(String methodName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findOverridingMethodsOf(CodeSearchEngine.Method method,
			InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findNewOf(String className, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findCastsTo(String typeName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findInstanceOf(String typeName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CodeSearchEngine.Method> findMethodsThrowing(
			String exceptionName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findCatchOf(String exceptionName, InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Type> findClassesAnnotatedWith(String annotationName,
			InputStream data) {
		// TODO Auto-generated method stub
		return null;
	}

}
