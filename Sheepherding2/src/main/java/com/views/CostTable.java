package com.views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.SystemColor;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.model.Cost;
import com.model.Materials;
import com.model.Model;
import com.model.VariableDefinitions;

import com.views.FrameLocationSetter;

import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class CostTable implements VariableDefinitions {

	private JFrame frmCosts;
	private JTable table;

	/**
	 * Create the application.
	 */
	public CostTable(Cost costs, Model model) {
		initialize(costs, model);
	}

	private int booleanToInt(boolean answer) {
		if(answer == true) {
			return 1;
		} else {
			return 0;
		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Cost costs, Model model) {
		frmCosts = new JFrame();
		frmCosts.setIconImage(Toolkit.getDefaultToolkit().getImage(CostTable.class.getResource("/com/resources/icon_sheep.png")));
		frmCosts.setTitle("Costs");
		frmCosts.setVisible(true);
		frmCosts.getContentPane().setBackground(new Color(168, 191, 174));
		frmCosts.getContentPane().setLayout(null);
		
		JLabel lblCosts = new JLabel("Cost table");
		lblCosts.setHorizontalAlignment(SwingConstants.CENTER);
		lblCosts.setVerticalAlignment(SwingConstants.TOP);
		lblCosts.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblCosts.setBounds(26, 13, 114, 30);
		lblCosts.setBorder(raisedbevel);
		lblCosts.setOpaque(true);
		lblCosts.setBackground(new Color(168, 191, 174));
		frmCosts.getContentPane().add(lblCosts);
		Materials materials = model.getMaterials();
		int getTractor = 0;
		if(materials.getNeedsNewTractor()) {
			getTractor = 1;
		}
		int getMower = 1 - booleanToInt(materials.getHasMower());
		int getShaker = 1 - booleanToInt(materials.getHasShaker());
		int getRaker = 1 - booleanToInt(materials.getHasRaker());
		int getFertilizerSpreader = 1 - booleanToInt(materials.getHasFertilizerSpreader());
		int getShaver = 1 - booleanToInt(materials.getHasShaver());
		double shaveCost = 0;
		if(!model.getCare().getWantsSelfShave()) {
			shaveCost = costs.getShaveOtherCost();
		}
		int getFertilizerPlate = 1 - booleanToInt(model.getShed().getHasFertilizerPlate());

		double shedDiff = model.getShed().getGoalCurSizeDiff();
		if(shedDiff <= 0) {
			shedDiff = 0;
		}
		
		int totalNSheepWanted = model.getSheep().getTotalNSheepWanted();
		int desiresNMoreSheep = model.getSheep().getDesiresNMoreSheep();
		
		int shaveCount = 0;
		if(costs.shaveOtherCost != 0) {
			shaveCount = totalNSheepWanted;
		} 
		
		double landNeeded = model.getLand().getLandNeeded();
		if(landNeeded < 0) {
			landNeeded = 0;
		}

		
		int slaughterSheep = 0;
		if(model.getCare().getWantsSlaughter()) {
			slaughterSheep = totalNSheepWanted;
		}
		
		String[] colNames = {"Item",		"Count", 	"Price"};
		Object[][] costsArray = {
						{"------------------ COSTS --------------------","-------------------------------------------------","-------------------------------------------------"},
						{"Tractor", 					getTractor+" tractor",					"�"+twoDigits.format(costs.getTractorCost())},
						{"Mower", 						getMower+" mower",						"�"+twoDigits.format(costs.getMowerCost())},
						{"Shaker",	 					getShaker+" shaker",					"�"+twoDigits.format(costs.getShakerCost())},
						{"Raker",	 					getRaker+" raker",						"�"+twoDigits.format(costs.getRakerCost())},
						{"Fertilizer spreader",			getFertilizerSpreader+" spreader",		"�"+twoDigits.format(costs.getFertilizerSpreaderCost())},
						{"Land needed", 				twoDigits.format(landNeeded)+" hectares leased",	"�"+twoDigits.format(costs.getLandNeededCost())},
						{"Shave other", 				shaveCount+" sheep",					"�"+twoDigits.format(shaveCost)},
						{"Shaving machine",				getShaver+" shaving machine",			"�"+twoDigits.format(costs.getShaverCost())},
						{"Myas Treatment", 				(totalNSheepWanted*3)+" treatments",	"�"+twoDigits.format(costs.getMyasTreatmentCost())},
						{"Worming",		 				totalNSheepWanted+" treatments",		"�"+twoDigits.format(costs.getWormCost())},
						{"Ear marks",	 				desiresNMoreSheep+" ear marks",			"�"+twoDigits.format(costs.getEarMarkCost())},
						{"RVO administration",			desiresNMoreSheep+" sheep",				"�"+twoDigits.format(costs.getRVOAdminCost())},
						{"Slaughter registration",		slaughterSheep+" sheep",				"�"+twoDigits.format(costs.getSlaughterCost())},
						{"Buying sheep",				desiresNMoreSheep+" sheep",				"�"+twoDigits.format(costs.getSheepBoughtCost())},
						{"Shed building",				twoDigits.format(shedDiff)+" square meters",		"�"+twoDigits.format(costs.getShedCost())},
						{"Movable fences",				twoDigits.format(costs.getLengthAdj())+" meters",	"�"+twoDigits.format(costs.getAdjFenceCost())},
						{"Eating fences",				twoDigits.format(costs.getLengthEat())+" meters",	"�"+twoDigits.format(costs.getEatFenceCost())},
						{"Mest plate",					getFertilizerPlate+" plate",			"�"+twoDigits.format(costs.getFertilizerPlateCost())},
						{"--------------- EARNINGS -------------------","-------------------------------------------------","-------------------------------------------------"},
						{"Sheep sold",					(totalNSheepWanted*2)+" lambs",			"�"+twoDigits.format(costs.getSheepSoldEarnings())},
						{"Wool Sold",					totalNSheepWanted+" coats of fur",		"�"+twoDigits.format(costs.getWoolEarnings())},
						{"Toeslagrechten", 				model.getLand().getLandSizeToeslag()+" hectares",	"�"+twoDigits.format(costs.getToeslagrechtEarnings())},
						{"---------------- TOTALS ---------------------","-------------------------------------------------","-------------------------------------------------"},
						{"Total costs",					"",										"�"+twoDigits.format(costs.getTotalCost())},
						{"Total earnings this year",	"",										"�"+twoDigits.format(costs.getTotalEarnings())},
						{"Costs minus earnings",		"",										"�"+twoDigits.format(costs.getMoneyNeeded())},
						{"----------- YOUR BUSINESS ---------------","-------------------------------------------------","-------------------------------------------------"},
						{"Money to spend",				"",										"�"+twoDigits.format(costs.getMoneyToSpend())},
						{"Your spending minus total needed",	"",								"�"+twoDigits.format((costs.getMoneyToSpend() - costs.getMoneyNeeded()))},
		};
		

		table = new JTable(costsArray, colNames);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setBackground(new Color(242, 242, 242));
		
		frmCosts.getContentPane().add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 56, 772, 510);
		frmCosts.getContentPane().add(scrollPane);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(10, 0, 772, 56);
		lblBackground.setOpaque(true);
		lblBackground.setBackground(new Color(208, 217, 209));
		lblBackground.setBorder(raisedbevel);
		frmCosts.getContentPane().add(lblBackground);

		frmCosts.setBounds(100, 100, 814, 626);
		frmCosts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FrameLocationSetter.setLocationToRight(frmCosts);
		FrameLocationSetter.setLocationToTop(frmCosts);
	}
}
