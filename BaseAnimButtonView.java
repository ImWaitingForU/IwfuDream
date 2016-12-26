package com.readboy.animview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Random;
/**

带动画的按钮

**/
public class BaseAnimButtonView extends ImageView{
	
	protected String TAG = "BaseAnimButtonView";
	public BaseAnimButtonView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BaseAnimButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BaseAnimButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}


	
	protected int[] ANIM_CHANGE_TERM = {AnimConstants.ANIM_CHANGE_TERM[0], AnimConstants.ANIM_CHANGE_TERM[1]};
	protected int ANIM_FRAME_TIME = 100;
	protected int backgroundDrawableId = 0;
	
	private int currframeId = 0;
	private int frameNumber = 0;
	private int firstFrameBmpId = 0; 
	private boolean playAnim = false;
	private Runnable playAnimRunnable = new Runnable()
	{
		@Override
		public void run() {
			if(playAnim)
			{
				playAnim();
			}
		}
	};

	protected void initAnim(int firstFrameBmpId, int frameNumber, int backgroundDrawableId)
	{
		this.backgroundDrawableId = backgroundDrawableId;
		this.firstFrameBmpId = firstFrameBmpId;
		this.frameNumber = frameNumber;
		autoChangeAnim();
	}
	
	
	
	private void playAnim()
	{
		if(currframeId >= frameNumber)
		{
//			if(!playAnim)
//			{
//				stopAnim();
//				return;
//			}
			currPlayTimes++;
			currframeId = 0;
			if(currPlayTimes >= playTimes)
			{
				stopAnim();
				return;
			}
		}
		
		setBackgroundResource(firstFrameBmpId+currframeId);
		currframeId++;
		postDelayed(playAnimRunnable, ANIM_FRAME_TIME);
	}
	
	
	public void stopAnim()
	{
		playAnim = false;
		currPlayTimes = 0;
		currframeId = 0;
		setBackgroundResource(backgroundDrawableId);
	}
	
	
	
	private Random autoAnimRandom = new Random();
	private boolean isAutoAnim = true;
	protected int stopAnimFrameId = 0;
	private Runnable autoAnimRunnable = new Runnable()
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(hasWindowFocus())
			{
				if(autoAnimRandom.nextBoolean())
				{
					if(!playAnim)
					{
						playAnim = true;
						playAnim();
					}
				}
				else
				{
//					MyLog.d(TAG, "mhc---autoAnimRunnable---stopAnim");
//					playAnim = false;
					stopAnim();
				}
			}
			
			autoChangeAnim();
		}
	};
	
	
	private void autoChangeAnim()
	{
		if (!isAutoAnim) {
			return;
		}
		postDelayed(autoAnimRunnable, (ANIM_CHANGE_TERM[0]+
				autoAnimRandom.nextInt(ANIM_CHANGE_TERM[1]))*1000);
	}
	
	
	public void setAutoChangeAnim(boolean auto)
	{
		isAutoAnim = auto;
		if(auto)
		{
			autoChangeAnim();
		}
	}
	
	
	private int playTimes = 1;
	private int currPlayTimes = 0;
	/**
	 * 设置播放次数
	 * @param playTimes
	 */
	public void setPlayTimes(int playTimes)
	{
		this.playTimes = playTimes;
	}
}
