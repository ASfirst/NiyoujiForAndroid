package com.jeramtough.niyouji.component.ali.sts;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 *         on 2018  January 09 Tuesday 21:38.
 */
@JtComponent
public class NiyoujiStsManager extends StsManager
{
	@IocAutowire
	public NiyoujiStsManager()
	{
		super("LTAIy7ouY87UNtr0", "pd4XmI1HgToTas1XMfmdtCt7w8OXlO",
				"acs:ram::1809179353317966:role/niyoujirole");
	}
}
