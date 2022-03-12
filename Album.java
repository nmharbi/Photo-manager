public class Album {
	
	String name;
	String condition;
	PhotoManager manager;
	
	// Constructor
	public Album(String name, String condition, PhotoManager manager) {
		this.name = name;
		this.condition = condition;
		this.manager = manager;
	}
	// Return the name of the album
	public String getName() {
		return name;
	}
	// Return the condition associated with the album
	public String getCondition() {
		return condition;
	}
	// Return the manager
	public PhotoManager getManager() {
		return manager;
	}
	// Return all photos that satisfy the album condition
	public LinkedList<Photo> getPhotos() {
		if(condition.equals("")) 
			return manager.AllPhotos;
		String[] conditions = null ;
		conditions = condition.split(" AND ");
		manager.index.findKey(conditions[0]);
		LinkedList<Photo> photos = manager.index.retrieve();
		for(int i = 0 ; i < conditions.length ; i++) {
			photos.findFirst();
			while(!photos.last2()) {
				boolean flag = true;
				LinkedList<String> tags = photos.retrieve().getTags();
				tags.findFirst();
				while(!tags.last2()) {
					if(tags.retrieve().equals(conditions[i]))
						flag = false;
					tags.findNext();
				}
				if(flag) 
					photos.remove();
				else
					photos.findNext();
			}
		}
		return photos;
}
	// Return the number of tag comparisons used to find all photos of the album
	public int getNbComps() {
		if (condition.equals(""))
			return manager.counter();
		String conditions[] = condition.split(" AND ");
		for (int i = 0; i < conditions.length; i++)
			conditions[i] = conditions[i].trim();
		BST<LinkedList<Photo>> index = manager.getPhotos();
		int totalComparisons = 0;
		for (int i = 0; i < conditions.length; i++)
			totalComparisons = totalComparisons + index.numberOfComparisons(conditions[i]);
		return totalComparisons;
	}
}
