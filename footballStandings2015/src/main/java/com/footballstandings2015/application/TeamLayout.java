package com.footballstandings2015.application;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TeamLayout extends LinearLayout {

	private TextView winsTitle, lossesTitle, tiesTitle, winPercentageTitle;
	private TextView wins, losses, ties, winPercentage;

	public TeamLayout(Context context, Team team) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.team_layout, this, true);

		TextView teamName = (TextView) this.findViewById(R.id.teamNameTextView);

		teamName.setText(team.getMarket() + " " + team.getName());

		assignVariables();

		assignData(team);
	}

	private void assignData(Team team) {

		// send the title textvews into underline method
		underlineTextView(winsTitle);
		underlineTextView(lossesTitle);
		underlineTextView(tiesTitle);
		underlineTextView(winPercentageTitle);

		wins.setText(team.getWins());
		losses.setText(team.getLosses());
		ties.setText(team.getTies());
		winPercentage.setText(team.getWin_percentage() + "%");

	}

	private void assignVariables() {

		winsTitle = (TextView) this.findViewById(R.id.winsTitleTextView);
		lossesTitle = (TextView) this.findViewById(R.id.lossesTitleTextView);
		tiesTitle = (TextView) this.findViewById(R.id.tiesTitleTextView);
		winPercentageTitle = (TextView) this
				.findViewById(R.id.winPercentageTitleTextView);

		wins = (TextView) this.findViewById(R.id.winsTextView);
		losses = (TextView) this.findViewById(R.id.lossesTextView);
		ties = (TextView) this.findViewById(R.id.tiesTextView);
		winPercentage = (TextView) this
				.findViewById(R.id.winPercentageTextView);

	}

	private void underlineTextView(TextView tv) {
		SpannableString content = new SpannableString(tv.getText());
		content.setSpan(new UnderlineSpan(), 0, tv.getText().length(), 0);
		tv.setText(content);
	}

	/**
	 * @return the winsTitle
	 */
	public TextView getWinsTitle() {
		return winsTitle;
	}

	/**
	 * @return the lossesTitle
	 */
	public TextView getLossesTitle() {
		return lossesTitle;
	}

	/**
	 * @return the tiesTitle
	 */
	public TextView getTiesTitle() {
		return tiesTitle;
	}

	/**
	 * @return the winPercentageTitle
	 */
	public TextView getWinPercentageTitle() {
		return winPercentageTitle;
	}

	/**
	 * @return the wins
	 */
	public TextView getWins() {
		return wins;
	}

	/**
	 * @return the losses
	 */
	public TextView getLosses() {
		return losses;
	}

	/**
	 * @return the ties
	 */
	public TextView getTies() {
		return ties;
	}

	/**
	 * @return the winPercentage
	 */
	public TextView getWinPercentage() {
		return winPercentage;
	}

	/**
	 * @param winsTitle
	 *            the winsTitle to set
	 */
	public void setWinsTitle(TextView winsTitle) {
		this.winsTitle = winsTitle;
	}

	/**
	 * @param lossesTitle
	 *            the lossesTitle to set
	 */
	public void setLossesTitle(TextView lossesTitle) {
		this.lossesTitle = lossesTitle;
	}

	/**
	 * @param tiesTitle
	 *            the tiesTitle to set
	 */
	public void setTiesTitle(TextView tiesTitle) {
		this.tiesTitle = tiesTitle;
	}

	/**
	 * @param winPercentageTitle
	 *            the winPercentageTitle to set
	 */
	public void setWinPercentageTitle(TextView winPercentageTitle) {
		this.winPercentageTitle = winPercentageTitle;
	}

	/**
	 * @param wins
	 *            the wins to set
	 */
	public void setWins(TextView wins) {
		this.wins = wins;
	}

	/**
	 * @param losses
	 *            the losses to set
	 */
	public void setLosses(TextView losses) {
		this.losses = losses;
	}

	/**
	 * @param ties
	 *            the ties to set
	 */
	public void setTies(TextView ties) {
		this.ties = ties;
	}

	/**
	 * @param winPercentage
	 *            the winPercentage to set
	 */
	public void setWinPercentage(TextView winPercentage) {
		this.winPercentage = winPercentage;
	}

}
