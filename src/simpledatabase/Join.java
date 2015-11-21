package simpledatabase;

import java.util.ArrayList;

public class Join extends Operator
{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	// Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate)
	{
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();

	}

	/**
	 * It is used to return a new tuple which is already joined by the common
	 * attribute
	 * 
	 * @return the new joined tuple
	 */
	// The record after join with two tables
	@Override
	public Tuple next()
	{
		Tuple left = leftChild.next(), right = rightChild.next();
		while (left != null)
		{
			tuples1.add(left);
			left = leftChild.next();
		}
		if (right == null)
		{
			return null;
		}
		else
		{
			for (int cnt = 0; cnt < tuples1.size(); cnt++)
			{
				left = tuples1.get(cnt);
				for (int i = 0; i < left.getAttributeList().size(); i++)
				{
					for (int j = 0; j < right.getAttributeList().size(); j++)
					{
						if (right.getAttributeName(j).equals(left.getAttributeName(i))
								&& right.getAttributeValue(j).equals(left.getAttributeValue(i)))
						{
							newAttributeList = new ArrayList<Attribute>();
							for (int k = 0; k < left.getAttributeList().size(); k++)
							{
								newAttributeList.add(left.getAttributeList().get(k));
							}
							for (int k = 0; k < right.getAttributeList().size(); k++)
							{
								newAttributeList.add(right.getAttributeList().get(k));
							}
							return new Tuple(newAttributeList);
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * The function is used to get the attribute list of the tuple
	 * 
	 * @return attribute list
	 */
	public ArrayList<Attribute> getAttributeList()
	{
		if (joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return (newAttributeList);
	}

}