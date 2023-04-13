package de.mdelab.migmm.history.timing.dag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import de.mdelab.migmm.history.timing.DAGVersion;
import de.mdelab.migmm.history.timing.TimingPackage;

public class DAGAdapter implements Adapter {

	private int currentID = 0;
	private List<DAGVersion> versions = new ArrayList<DAGVersion>();
	private Collection<DAGVersion>[] exclusivePredecessors;
	private Collection<DAGVersion>[][] earliestCommonSuccessors;
//	private Collection<Collection<DAGVersion>>[][] allPaths;
	
	@SuppressWarnings("unchecked")
	@Override
	public void notifyChanged(Notification notification) {
		if(notification.getFeature() == TimingPackage.Literals.DAG_VERSION__SUCCESSORS) {
			List<DAGVersion> newSuccessors;
			if(notification.getEventType() == Notification.ADD) {
				newSuccessors = Collections.singletonList((DAGVersion) notification.getNewValue());
			}
			else if(notification.getEventType() == Notification.ADD_MANY) {
				newSuccessors = (List<DAGVersion>) notification.getNewValue();
			}
			else {
				newSuccessors = Collections.emptyList();
			}
			
			for(DAGVersion newSuccessor:newSuccessors) {
				if(!newSuccessor.eAdapters().contains(this)) {
					addAdapter(newSuccessor);
				}
				refreshIndices(newSuccessor);
			}
		}
	}

	public void refreshIndices(DAGVersion newSuccessor) {
		if(newSuccessor.getSuccessors().size() == 0) {
			refreshPredecessorIndex(newSuccessor);
			refreshSuccessorIndex(newSuccessor);
//			refreshPathsIndex(newSuccessor);
		}
		else {
			System.out.println("???");
			refreshPredecessorIndex();
			refreshSuccessorIndex();
//			refreshPathsIndex();
		}
	}

	public void refreshIndices() {
		refreshPredecessorIndex();
		refreshSuccessorIndex();
//		refreshPathsIndex();
	}

	public void addAdapter(DAGVersion v) {
		v.eAdapters().add(this);
		versions.add(v);
		v.setIntID(currentID);
		currentID++;
	}

	public Collection<DAGVersion> getEarliestCommonSuccessors(DAGVersion v1, DAGVersion v2) {
		return earliestCommonSuccessors[v1.getIntID()][v2.getIntID()];
	}

	public Collection<DAGVersion> getExclusivePredecessors(DAGVersion v) {
		return exclusivePredecessors[v.getIntID()];
	}

//	public Collection<Collection<DAGVersion>> getAllPaths(DAGVersion source, DAGVersion target) {
//		return allPaths[source.getIntID()][target.getIntID()];
//	}
//
//	@SuppressWarnings("unchecked")
//	public void refreshPathsIndex(DAGVersion newSuccessor) {
//		Collection<Collection<DAGVersion>> [][] oldPaths = allPaths;
//		allPaths = (Collection<Collection<DAGVersion>> [][]) new Collection[currentID][currentID];
//		for(int i = 0; i < currentID - 1; i++) {
//			for(int j = 0; j < currentID - 1; j++) {
//				allPaths[i][j] = oldPaths[i][j];
//			}
//		}
//		
//		for(int i = 0; i < currentID - 1; i++) {
//			allPaths[i][newSuccessor.getIntID()] = new ArrayList<Collection<DAGVersion>>();
//			allPaths[newSuccessor.getIntID()][i] = new ArrayList<Collection<DAGVersion>>();
//			for(DAGVersion predecessor:newSuccessor.getPredecessors()) {
//				for(Collection<DAGVersion> predecessorPath:allPaths[i][predecessor.getIntID()]) {
//					Collection<DAGVersion> successorPath = new LinkedHashSet<DAGVersion>(predecessorPath);
//					successorPath.add(newSuccessor);
//					allPaths[i][newSuccessor.getIntID()].add(successorPath);
//				}
//			}
//		}
//		allPaths[newSuccessor.getIntID()][newSuccessor.getIntID()] = new ArrayList<Collection<DAGVersion>>();
//		allPaths[newSuccessor.getIntID()][newSuccessor.getIntID()].add(Collections.singletonList(newSuccessor));
//	}
//	
//	@SuppressWarnings("unchecked")
//	public void refreshPathsIndex() {
//		allPaths = (Collection<Collection<DAGVersion>> [][]) new Collection[currentID][currentID];
//		for(int i = 0; i < currentID; i++) {
//			for(int j = 0; j < currentID; j++) {
//				allPaths[i][j] = new ArrayList<Collection<DAGVersion>>();
//			}
//		}
//		
//		for(DAGVersion version:versions) {
//			indexAllPaths(version);
//		}
//	}
//
//	private void indexAllPaths(DAGVersion version) {
//		LinkedList<List<DAGVersion>> paths = new LinkedList<List<DAGVersion>>();
//		List<DAGVersion> initialPath = new LinkedList<DAGVersion>();
//		initialPath.add(version);
//		paths.push(initialPath);
//		
//		while(!paths.isEmpty()) {
//			List<DAGVersion> path = paths.pop();
//			DAGVersion target = path.get(path.size() - 1);
//			allPaths[version.getIntID()][target.getIntID()].add(new LinkedHashSet<DAGVersion>(path));
//			
//			for(DAGVersion successor:target.getSuccessors()) {
//				List<DAGVersion> successorPath = new LinkedList<DAGVersion>(path);
//				successorPath.add(successor);
//				paths.push(successorPath);
//			}
//		}
//	}

	public void refreshSuccessorIndex(DAGVersion newSuccessor) {
		if(currentID >= earliestCommonSuccessors.length) {
			growIndices();
		}
		
		for(int i = 0; i < currentID; i++) {
			earliestCommonSuccessors[i][newSuccessor.getIntID()] = new ArrayList<DAGVersion>();
			earliestCommonSuccessors[newSuccessor.getIntID()][i] = new ArrayList<DAGVersion>();
		}
		
		earliestCommonSuccessors[newSuccessor.getIntID()][newSuccessor.getIntID()] = Collections.singletonList(newSuccessor);
		
		Collection<DAGVersion> newPredecessors = exclusivePredecessors[newSuccessor.getIntID()];
		for(DAGVersion predecessor:newPredecessors) {
			earliestCommonSuccessors[predecessor.getIntID()][newSuccessor.getIntID()] = Collections.singletonList(newSuccessor);
			earliestCommonSuccessors[newSuccessor.getIntID()][predecessor.getIntID()] = Collections.singletonList(newSuccessor);
		}
		
		if(newSuccessor.getPredecessors().size() > 1) {
			
			if(newSuccessor.getPredecessors().size() == 2) {
				DAGVersion p1 = newSuccessor.getPredecessors().get(0);
				DAGVersion p2 = newSuccessor.getPredecessors().get(1);
				
				Collection<DAGVersion> p1Predecessors = new LinkedHashSet<DAGVersion>();
				LinkedList<DAGVersion> queue = new LinkedList<DAGVersion>();
				queue.add(p1);
				while(!queue.isEmpty()) {
					DAGVersion current = queue.poll();
					if(p1Predecessors.contains(current)) {
						continue;
					}
					
					if(!exclusivePredecessors[p2.getIntID()].contains(current)) {
						p1Predecessors.add(current);
						for(DAGVersion currentPredecessor:current.getPredecessors()) {
							queue.add(currentPredecessor);
						}
					}
				}
				
				Collection<DAGVersion> p2Predecessors = new LinkedHashSet<DAGVersion>();
				queue = new LinkedList<DAGVersion>();
				queue.add(p2);
				while(!queue.isEmpty()) {
					DAGVersion current = queue.poll();
					if(p2Predecessors.contains(current)) {
						continue;
					}
					
					if(!exclusivePredecessors[p1.getIntID()].contains(current)) {
						p2Predecessors.add(current);
						for(DAGVersion currentPredecessor:current.getPredecessors()) {
							queue.add(currentPredecessor);
						}
					}
				}
				
				for(DAGVersion p1Predecessor:p1Predecessors) {
					for(DAGVersion p2Predecessor:p2Predecessors) {
						earliestCommonSuccessors[p1Predecessor.getIntID()][p2Predecessor.getIntID()].add(newSuccessor);
						earliestCommonSuccessors[p2Predecessor.getIntID()][p1Predecessor.getIntID()].add(newSuccessor);
					}
				}
				
			}
			else {
				for(DAGVersion predecessor:newPredecessors) {
					for(DAGVersion predecessor2:newPredecessors) {
						boolean addSuccessor = true;
						Collection<DAGVersion> oldCommonSuccessors = earliestCommonSuccessors[predecessor.getIntID()][predecessor2.getIntID()];
						for(DAGVersion oldCommonSuccessor:oldCommonSuccessors) {
							if(newPredecessors.contains(oldCommonSuccessor)) {
								addSuccessor = false;
								break;
							}
						}
						if(addSuccessor) {
							earliestCommonSuccessors[predecessor.getIntID()][predecessor2.getIntID()].add(newSuccessor);
							earliestCommonSuccessors[predecessor2.getIntID()][predecessor.getIntID()].add(newSuccessor);
						}
					}
				}
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	private void growIndices() {
		Collection<DAGVersion>[] oldPredecessors = exclusivePredecessors;
		exclusivePredecessors = (Collection<DAGVersion>[]) new Collection[currentID * 2];
		for(int i = 0; i < currentID - 1; i++) {
			exclusivePredecessors[i] = oldPredecessors[i];
		}
		
		
		Collection<DAGVersion>[][] oldSuccessors = earliestCommonSuccessors;
		earliestCommonSuccessors = (Collection<DAGVersion> [][]) new Collection[currentID * 2][currentID * 2];

		for(int i = 0; i < currentID - 1; i++) {
			for(int j = 0; j < currentID - 1; j++) {
				earliestCommonSuccessors[i][j] = oldSuccessors[i][j];
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void refreshSuccessorIndex() {
		earliestCommonSuccessors = (Collection<DAGVersion> [][]) new Collection[currentID][currentID];

		Collection<DAGVersion>[] allSuccessors = (Collection<DAGVersion> []) new Collection[currentID];
		for(DAGVersion version:versions) {
			allSuccessors[version.getIntID()] = computeAllSuccessors(version);
		}
		
		for(int i = 0; i < versions.size(); i++) {
			DAGVersion v1 = versions.get(i);
			earliestCommonSuccessors[v1.getIntID()][v1.getIntID()] = Collections.singleton(v1);
		}
		
		for(int i = 0; i < versions.size() - 1; i++) {
			DAGVersion v1 = versions.get(i);
			
			earliestCommonSuccessors[v1.getIntID()][v1.getIntID()] = Collections.singleton(v1);
			
			for(int j = i + 1; j < versions.size(); j++) {
				DAGVersion v2 = versions.get(j);
				
				Collection<DAGVersion> commonSuccessors = new LinkedHashSet<DAGVersion>(allSuccessors[v1.getIntID()]);
				commonSuccessors.retainAll(allSuccessors[v2.getIntID()]);
				
				Collection<DAGVersion> earliestSuccessors = new ArrayList<DAGVersion>();
				for(DAGVersion candidate:commonSuccessors) {
					Collection<DAGVersion> candidatePredecessors = exclusivePredecessors[candidate.getIntID()];
					
					Collection<DAGVersion> intersection = new LinkedHashSet<DAGVersion>(candidatePredecessors);
					intersection.retainAll(commonSuccessors);
					
					if(intersection.isEmpty()) {
						earliestSuccessors.add(candidate);
					}
				}

				earliestCommonSuccessors[v1.getIntID()][v2.getIntID()] = earliestSuccessors;
				earliestCommonSuccessors[v2.getIntID()][v1.getIntID()] = earliestSuccessors;
			}
		}
	}

	public void refreshPredecessorIndex(DAGVersion newSuccessor) {
		if(currentID >= exclusivePredecessors.length) {
			growIndices();
		}
		exclusivePredecessors[newSuccessor.getIntID()] = computeExclusivePredecessors(newSuccessor);
	}

	@SuppressWarnings("unchecked")
	public void refreshPredecessorIndex() {
		exclusivePredecessors = (Collection<DAGVersion> []) new Collection[currentID];
		for(DAGVersion version:versions) {
			exclusivePredecessors[version.getIntID()] = computeExclusivePredecessors(version);
		}
	}

	private Collection<DAGVersion> computeAllSuccessors(DAGVersion version) {
		LinkedList<DAGVersion> queue = new LinkedList<DAGVersion>();
		queue.add(version);
		
		Set<DAGVersion> allSuccessors = new LinkedHashSet<DAGVersion>();
		allSuccessors.add(version);
		
		while(!queue.isEmpty()) {
			DAGVersion successor = queue.poll();
			
			for(DAGVersion nextSuccessor:successor.getSuccessors()) {
				if(!allSuccessors.contains(nextSuccessor)) {
					queue.add(nextSuccessor);
					allSuccessors.add(nextSuccessor);
				}
			}
		}
		
		return allSuccessors;
	}

	private Collection<DAGVersion> computeExclusivePredecessors(DAGVersion version) {
		LinkedList<DAGVersion> queue = new LinkedList<DAGVersion>();
		queue.add(version);
		
		Set<DAGVersion> exclusivePredecessors = new LinkedHashSet<DAGVersion>();
		
		while(!queue.isEmpty()) {
			DAGVersion predecessor = queue.poll();
			
			for(DAGVersion previousPredecessor:predecessor.getPredecessors()) {
				if(!exclusivePredecessors.contains(previousPredecessor)) {
					queue.add(previousPredecessor);
					exclusivePredecessors.add(previousPredecessor);
				}
			}
		}
		
		return exclusivePredecessors;
	}

	@Override
	public Notifier getTarget() {
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return false;
	}
}
