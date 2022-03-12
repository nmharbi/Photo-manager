public class PhotoManager {
	
	BST<LinkedList<Photo>> index;
	LinkedList<Photo> AllPhotos;
	private int counter = 0;

	// Constructor
	public PhotoManager() {
		index = new BST<LinkedList<Photo>>();
		AllPhotos = new LinkedList<Photo>();
	}
	public boolean contains(Photo p) {
		if(AllPhotos.empty())
			return true;
		AllPhotos.findFirst();
		while(AllPhotos.last2()) {
			if( AllPhotos.retrieve().equals(p))
				return false;
			AllPhotos.findNext();
		}
		while(AllPhotos.last2()) 
			AllPhotos.findNext();
		return true;
	}
	// Add a photo
	public void addPhoto(Photo p) {
		if(p.getTags() == null)
			return;
		if(contains(p))
			AllPhotos.insert(p);
		LinkedList<String> tag = p.getTags();
		tag.findFirst();
		while(!tag.last2()) {
				if(!index.findKey(tag.retrieve())) {
					LinkedList<Photo>Photos = new LinkedList<Photo>();
					Photos.insert(p);
					index.insert(tag.retrieve(),Photos);
					counter++;
				}else {
					LinkedList<Photo>Photos = index.retrieve();
					Photos.insert(p);
					index.update(Photos);
				}
				tag.findNext();
		}
	}
	// Delete a photo
	public void deletePhoto(String path) {
		if(index.empty())
			return;
		AllPhotos.findFirst();
		while(!AllPhotos.last2()) {
			if(AllPhotos.retrieve().getPath().equals(path)) {
				LinkedList<String> tags = AllPhotos.retrieve().getTags();
				tags.findFirst();
				while(!tags.last2()) {
					index.findKey(tags.retrieve());
					LinkedList<Photo> Photos = index.retrieve();
					Photos.findFirst();
					while(!Photos.last2()){
						if(Photos.retrieve().getPath().equals(path)) {
							Photos.remove();
							if(Photos.empty() == true)
								index.removeKey(tags.retrieve());
							break;
							}
						Photos.findNext();
						}
					tags.findNext();
					}
				}
			AllPhotos.findNext();
		}
	}
	// Return the inverted index of all managed photos
	public BST<LinkedList<Photo>> getPhotos() {
		return index;
	}

	public int counter() {
		return counter;
}
}