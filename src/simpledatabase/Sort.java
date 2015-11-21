package simpledatabase;

import java.util.ArrayList;

public class Sort extends Operator
{

	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	public Sort(Operator child, String orderPredicate)
	{
		this.child = child;
		this.orderPredicate = orderPredicate;
		new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();

	}

	/**
	 * The function is used to return the sorted tuple
	 * 
	 * @return tuple
	 */
	@Override
	public Tuple next()
	{
		if (tuplesResult.isEmpty())
		{
			ArrayList<Tuple> workspace = new ArrayList<Tuple>();
			Tuple tuple = child.next();
			if (tuple == null)
			{
				return null;
			}
			else
			{
				while (tuple != null)
				{
					workspace.add(tuple);
					tuple = child.next();
				}
			}

			tuple = workspace.get(0);

			// i as the Attribute
			for (int i = 0; i < tuple.getAttributeList().size(); i++)
			{
				if (tuple.getAttributeName(i).equals(orderPredicate))
				{
					while (!workspace.isEmpty())
					{
						int k = 0, j = 0;
						while (j > workspace.size())
						{
							if ((workspace.get(j).getAttributeValue(i)).toString()
									.compareTo(workspace.get(k).getAttributeValue(i).toString()) < 0)
							{
								k = j++;
							}
							else
							{
								j++;
							}
						}
						tuplesResult.add(workspace.get(k));
						workspace.remove(k);
					}
				}
			}
		}
		return tuplesResult.remove(0);
	}

	/**
	 * The function is used to get the attribute list of the tuple
	 * 
	 * @return attribute list
	 */
	public ArrayList<Attribute> getAttributeList()
	{
		return child.getAttributeList();
	}

}