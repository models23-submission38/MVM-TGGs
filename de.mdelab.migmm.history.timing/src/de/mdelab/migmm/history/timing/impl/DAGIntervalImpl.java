/**
 */
package de.mdelab.migmm.history.timing.impl;

import de.mdelab.migmm.history.History;
import de.mdelab.migmm.history.HistoryPackage;
import de.mdelab.migmm.history.Interval;
import de.mdelab.migmm.history.TimingUnit;

import de.mdelab.migmm.history.timing.DAGInterval;
import de.mdelab.migmm.history.timing.DAGVersion;
import de.mdelab.migmm.history.timing.TimingFactory;
import de.mdelab.migmm.history.timing.TimingPackage;
import de.mdelab.migmm.history.timing.dag.DAGAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DAG Interval</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.mdelab.migmm.history.timing.impl.DAGIntervalImpl#getCts <em>Cts</em>}</li>
 *   <li>{@link de.mdelab.migmm.history.timing.impl.DAGIntervalImpl#getDts <em>Dts</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DAGIntervalImpl extends MinimalEObjectImpl.Container implements DAGInterval {
	/**
	 * The cached value of the '{@link #getCts() <em>Cts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCts()
	 * @generated
	 * @ordered
	 */
	protected EList<DAGVersion> cts;

	/**
	 * The cached value of the '{@link #getDts() <em>Dts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDts()
	 * @generated
	 * @ordered
	 */
	protected EList<DAGVersion> dts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DAGIntervalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TimingPackage.Literals.DAG_INTERVAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DAGVersion> getCts() {
		if (cts == null) {
			cts = new EObjectResolvingEList<DAGVersion>(DAGVersion.class, this, TimingPackage.DAG_INTERVAL__CTS);
		}
		return cts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DAGVersion> getDts() {
		if (dts == null) {
			dts = new EObjectResolvingEList<DAGVersion>(DAGVersion.class, this, TimingPackage.DAG_INTERVAL__DTS);
		}
		return dts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public Interval intersect(Interval i) {
		if(!(i.eClass() == TimingPackage.Literals.DAG_INTERVAL)) {
			return null;
		}
		DAGInterval other = (DAGInterval) i;
		
		DAGInterval result;
		
		long start = System.nanoTime();
		if(this.getCts().isEmpty() || other.getCts().isEmpty()) {
			result = create(Collections.emptySet(), Collections.emptySet());
		}
		else if (this.getCts().size() == 1 && other.getCts().size() == 1 &&
				this.getDts().isEmpty() && other.getDts().isEmpty()){
			DAGVersion cts1 = this.getCts().get(0);
			DAGVersion cts2 = other.getCts().get(0);
			
			DAGAdapter dagAdapter = getDAGAdapter(cts1);
			
			if(dagAdapter != null) {
				Collection<DAGVersion> newCts = dagAdapter.getEarliestCommonSuccessors(cts1, cts2);				
				result = create(newCts, Collections.emptyList());
			}
			else {
				Set<DAGVersion> covered = computeCoveredVersions();
				covered.retainAll(other.computeCoveredVersions());
				
				result = createEncoding(covered);
			}
		}
		else {
			Set<DAGVersion> covered = computeCoveredVersions();
			covered.retainAll(other.computeCoveredVersions());
			
			result = createEncoding(covered);
		}

		long end = System.nanoTime();
		
		INTERSECT_TIME += (end - start);
		
		return result;
	}

	public static long INTERSECT_TIME = 0;
	
//	/**
//	 * <!-- begin-user-doc -->
//	 * <!-- end-user-doc -->
//	 * @generated not
//	 */
//	private boolean addToIntersectionCts(DAGVersion c, DAGVersion cts1, DAGVersion cts2,
//			Collection<DAGVersion> candidateDts, DAGAdapter dagAdapter) {
//		
//		boolean addToCts1 = false;
//		for(Collection<DAGVersion> path:dagAdapter.getAllPaths(cts1, c)) {
//			boolean pathBlocked = false;
//			for(DAGVersion d:candidateDts) {
//				if(path.contains(d)) {
//					pathBlocked = true;
//					break;
//				}
//			}
//			if(!pathBlocked) {
//				addToCts1 = true;
//				break;
//			}
//		}
//		
//		if(!addToCts1) {
//			return false;
//		}
//
//		boolean addToCts2 = false;
//		for(Collection<DAGVersion> path:dagAdapter.getAllPaths(cts2, c)) {
//			boolean pathBlocked = false;
//			for(DAGVersion d:candidateDts) {
//				if(path.contains(d)) {
//					pathBlocked = true;
//					break;
//				}
//			}
//			if(!pathBlocked) {
//				addToCts2 = true;
//				break;
//			}
//		}
//		if(!addToCts2) {
//			return false;
//		}
//		
//		return true;
//	}

//	/**
//	 * <!-- begin-user-doc -->
//	 * <!-- end-user-doc -->
//	 * @generated not
//	 */
//	private boolean addToIntersectionDts(DAGVersion d, Collection<DAGVersion> newCts,
//			Collection<DAGVersion> candidateDts, DAGAdapter dagAdapter) {
//		
//		for(DAGVersion cts:newCts) {
//			for(Collection<DAGVersion> path:dagAdapter.getAllPaths(cts, d)) {
//				boolean pathBlocked = false;
//				for(DAGVersion d2:candidateDts) {
//					if(d != d2 && path.contains(d2)) {
//						pathBlocked = true;
//						break;
//					}
//				}
//				if(!pathBlocked) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	private DAGAdapter getDAGAdapter(DAGVersion v) {
		for(Adapter a:v.eAdapters()) {
			if(a instanceof DAGAdapter) {
				return (DAGAdapter) a;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public Interval minus(Interval i) {
		if(!(i.eClass() == TimingPackage.Literals.DAG_INTERVAL)) {
			return null;
		}
		
		long start = System.nanoTime();
		DAGInterval other = (DAGInterval) i;
		
		Set<DAGVersion> covered = computeCoveredVersions();
		covered.removeAll(other.computeCoveredVersions());

		DAGInterval result = createEncoding(covered);
		long end = System.nanoTime();
		
		MINUS_TIME += (end - start);
		
		return result;
	}

	public static long MINUS_TIME = 0;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public Interval union(Interval i) {
		if(!(i.eClass() == TimingPackage.Literals.DAG_INTERVAL)) {
			return null;
		}
		
		long start = System.nanoTime();
		
		DAGInterval other = (DAGInterval) i;
		
		Set<DAGVersion> covered = computeCoveredVersions();
		covered.addAll(other.computeCoveredVersions());

		DAGInterval result = createEncoding(covered);
		long end = System.nanoTime();
		
		UNION_TIME += (end - start);
		
		return result;
	}

	public static long UNION_TIME = 0;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Interval complement() {
		long start = System.nanoTime();
		Set<DAGVersion> covered = computeCoveredVersions();

		Set<DAGVersion> complement = getAllVersions();
		complement.removeAll(covered);

		DAGInterval result =  createEncoding(complement);
		
		long end = System.nanoTime();
		
		COMPLEMENT_TIME += (end - start);
		
		return result;
	}
	
	public static long COMPLEMENT_TIME = 0;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	private Set<DAGVersion> getAllVersions() {
		EObject history = getCts().get(0).eContainer();
		while(history.eClass() != HistoryPackage.Literals.HISTORY) {
			history = history.eContainer();
		}

		Set<DAGVersion> result = new LinkedHashSet<DAGVersion>();
		
//		if(container.eClass() == HistoryPackage.Literals.HISTORY) {
			for(TimingUnit t:((History) history).getOwnedTimingUnits()) {
				if(t.eClass() == TimingPackage.Literals.DAG_VERSION) {
					result.add((DAGVersion) t);
				}
			}
//		}
		
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	private Collection<DAGVersion> getDistinctOriginVersions(DAGVersion version) {
		Set<DAGVersion> originVersions = new LinkedHashSet<DAGVersion>();
		Set<DAGVersion> visitedVersions = new LinkedHashSet<DAGVersion>();
		visitedVersions.add(version);
		
		Queue<DAGVersion> queue = new LinkedList<DAGVersion>();
		for(DAGVersion predecessor:version.getPredecessors()) {
			queue.add(predecessor);
		}

		while(!queue.isEmpty()) {
			DAGVersion predecessor = queue.poll();
			if(!visitedVersions.contains(predecessor)) {
				if(predecessor.getPredecessors().isEmpty()) {
					originVersions.add(predecessor);
				}
				else {
					for(DAGVersion predecessor2:predecessor.getPredecessors()) {
						queue.add(predecessor2);
					}
				}
				
				visitedVersions.add(predecessor);
			}
		}
		return originVersions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public boolean contains(TimingUnit t) {
		DAGAdapter dagAdapter = getDAGAdapter((DAGVersion) t);
		if(getDts().isEmpty() && dagAdapter != null) {
			for(DAGVersion c:getCts()) {
				if(c == t || dagAdapter.getExclusivePredecessors((DAGVersion) t).contains(c)) {
					return true;
				}
			}
			return false;
		}
		else {
			return computeCoveredVersions().contains(t);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public boolean isEmpty() {
		return computeCoveredVersions().isEmpty();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public EList<?> getPresentTiming() {
		return new BasicEList<DAGVersion>(computeCoveredVersions());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public EList<?> getDeletedTiming() {
		Set<DAGVersion> deletedVersions = new LinkedHashSet<DAGVersion>();
		deletedVersions.addAll(getDts());
		Queue<DAGVersion> queue = new LinkedList<DAGVersion>();
		queue.addAll(getDts());
		
		while(!queue.isEmpty()) {
			DAGVersion current = queue.poll();
			for(DAGVersion successor:current.getSuccessors()) {
				if(!deletedVersions.contains(successor) && !getCts().contains(successor)) {
					deletedVersions.add(successor);
					queue.add(successor);
				}
			}
		}
		
		return new BasicEList<DAGVersion>(deletedVersions);		
//		Set<DAGVersion> coveredVersions = computeCoveredVersions();
//		
//		Set<DAGVersion> deletedVersions = computeReachableVersions();
//		deletedVersions.removeAll(coveredVersions);
//		
//		return new BasicEList<DAGVersion>(deletedVersions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<?> getAbsentTiming() {
		Set<DAGVersion> reachableVersions = computeReachableVersions();
		
		Set<DAGVersion> absentVersions = new LinkedHashSet<DAGVersion>((Collection<? extends DAGVersion>) ((History) getCts().get(0).eContainer()).getOwnedTimingUnits());
		absentVersions.removeAll(reachableVersions);
		
		return new BasicEList<DAGVersion>(absentVersions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 * 
	 */
	public Set<DAGVersion> computeCoveredVersions() {		
		Set<DAGVersion> coveredVersions = new LinkedHashSet<DAGVersion>();
		coveredVersions.addAll(getCts());
		Queue<DAGVersion> queue = new LinkedList<DAGVersion>();
		queue.addAll(getCts());
		
		while(!queue.isEmpty()) {
			DAGVersion current = queue.poll();
			for(DAGVersion successor:current.getSuccessors()) {
				if(!coveredVersions.contains(successor) && !getDts().contains(successor)) {
					coveredVersions.add(successor);
					queue.add(successor);
				}
			}
		}
		
		return coveredVersions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 * 
	 */
	private Set<DAGVersion> computeReachableVersions() {
		Set<DAGVersion> reachableVersions = new LinkedHashSet<DAGVersion>();
		reachableVersions.addAll(getCts());
		Queue<DAGVersion> queue = new LinkedList<DAGVersion>();
		queue.addAll(getCts());
		
		while(!queue.isEmpty()) {
			DAGVersion current = queue.poll();
			for(DAGVersion successor:current.getSuccessors()) {
				if(!reachableVersions.contains(successor)) {
					reachableVersions.add(successor);
					queue.add(successor);
				}
			}
		}
		return reachableVersions;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TimingPackage.DAG_INTERVAL__CTS:
				return getCts();
			case TimingPackage.DAG_INTERVAL__DTS:
				return getDts();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TimingPackage.DAG_INTERVAL__CTS:
				getCts().clear();
				getCts().addAll((Collection<? extends DAGVersion>)newValue);
				return;
			case TimingPackage.DAG_INTERVAL__DTS:
				getDts().clear();
				getDts().addAll((Collection<? extends DAGVersion>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TimingPackage.DAG_INTERVAL__CTS:
				getCts().clear();
				return;
			case TimingPackage.DAG_INTERVAL__DTS:
				getDts().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TimingPackage.DAG_INTERVAL__CTS:
				return cts != null && !cts.isEmpty();
			case TimingPackage.DAG_INTERVAL__DTS:
				return dts != null && !dts.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TimingPackage.DAG_INTERVAL___INTERSECT__INTERVAL:
				return intersect((Interval)arguments.get(0));
			case TimingPackage.DAG_INTERVAL___MINUS__INTERVAL:
				return minus((Interval)arguments.get(0));
			case TimingPackage.DAG_INTERVAL___UNION__INTERVAL:
				return union((Interval)arguments.get(0));
			case TimingPackage.DAG_INTERVAL___COMPLEMENT:
				return complement();
			case TimingPackage.DAG_INTERVAL___CONTAINS__TIMINGUNIT:
				return contains((TimingUnit)arguments.get(0));
			case TimingPackage.DAG_INTERVAL___IS_EMPTY:
				return isEmpty();
			case TimingPackage.DAG_INTERVAL___GET_PRESENT_TIMING:
				return getPresentTiming();
			case TimingPackage.DAG_INTERVAL___GET_DELETED_TIMING:
				return getDeletedTiming();
			case TimingPackage.DAG_INTERVAL___GET_ABSENT_TIMING:
				return getAbsentTiming();
		}
		return super.eInvoke(operationID, arguments);
	}

	public static DAGInterval createEncoding(Set<DAGVersion> timepoints) {
		Set<DAGVersion> cts = new LinkedHashSet<DAGVersion>();
		Set<DAGVersion> dts = new LinkedHashSet<DAGVersion>();
		
		for(DAGVersion version:timepoints) {
			boolean hasPredecessor = false;
			for(DAGVersion predecessor:version.getPredecessors()) {
				if(timepoints.contains(predecessor)) {
					hasPredecessor = true;
					break;
				}
			}
			if(!hasPredecessor) {
				cts.add(version);
			}
			
			for(DAGVersion successor:version.getSuccessors()) {
				if(!timepoints.contains(successor)) {
					dts.add(successor);
				}
			}
		}
		
		return create(cts, dts);
	}

	public static DAGInterval create(DAGInterval d) {
		return create(d.getCts(), d.getDts());
	}

	public static DAGInterval create(Collection<DAGVersion> cts, Collection<DAGVersion> dts) {
		DAGInterval i = TimingFactory.eINSTANCE.createDAGInterval();
		i.getCts().addAll(cts);
		i.getDts().addAll(dts);
		return i;
	}
	
} //DAGIntervalImpl
