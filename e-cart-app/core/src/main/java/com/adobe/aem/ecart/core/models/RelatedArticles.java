package com.adobe.aem.ecart.core.models;



import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {Resource.class}, adapters = RelatedArticles.class)
public class RelatedArticles {
	
	@ValueMapValue
	private String articlepath;
	
	@ValueMapValue
	private String articletitle;

	public String getArticlepath() {
		return articlepath;
	}

	public String getArticletitle() {
		return articletitle;
	}

	public void setArticlepath(String articlepath) {
		this.articlepath = articlepath;
	}

	public void setArticletitle(String articletitle) {
		this.articletitle = articletitle;
	}

	
	
}
