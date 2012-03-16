package net.toxbank.isa;

import com.hp.hpl.jena.ontology.Individual;

/**
 * A named node
 * @author nina
 *
 * @param <CT>
 * @param <T>
 */
public class ANode<CT extends TemplateCollection,T extends TemplateRow<CT>> extends AnyISAObject<Individual> {

	public ANode(String uri,T template) throws Exception {
		super(template.createInstance(uri));
	}

}