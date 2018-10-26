package verticalPlateformer.plateforme;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import verticalPlateformer.Player;
import verticalPlateformer.World;


public class PlateformeGen {
	private static Image plateformeH1;
	private static Image plateformeH2;
	private static Image plateformeV1;
	private static Image plateformeV2;
	private static Image portalO;
	private static Image portalB;
	private World world;
	private Random r;
	private int compt;
	private int totalCompt;
	private Player p;
	
	static {
		try {
			plateformeH1 = new Image("images/verticalPlateformer/plateformeH1.png");
			plateformeH2 = new Image("images/verticalPlateformer/plateformeH2.png");
			plateformeV1 = new Image("images/verticalPlateformer/plateformeV1.png");
			plateformeV2 = new Image("images/verticalPlateformer/plateformeV2.png");
			portalO = new Image("images/verticalPlateformer/orangePortal.png");
			portalB = new Image("images/verticalPlateformer/bluePortal.png");
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public PlateformeGen(World w, Player p) {
		world = w;
		r = new Random();
		compt = r.nextInt(6);
		totalCompt=1;
		this.p=p;
		w.addPlateforme(new PlateformeClassique(w.getWidth()/2-65, 90, 200, 30, true, p, plateformeH1));
	}
	
	public void createPlateforme(GameContainer container, boolean classique, boolean horizontale) {
		if (horizontale) {
			Image image = r.nextBoolean() ? plateformeH1 : plateformeH2 ;
			if (classique) {
				world.addPlateforme(new PlateformeClassique(r.nextInt(container.getWidth()-400)+100,container.getHeight()/2-500*totalCompt,200,30,horizontale,p,image));
			} else {
				world.addPlateforme(new PlateformeMouvante(r.nextInt(container.getWidth()-800)+200,container.getHeight()/2-500*totalCompt,200,30,horizontale,p,image));
			}
		} else {
			Image image = r.nextBoolean() ? plateformeV1 : plateformeV2 ;
			if (classique) {
				int i = r.nextInt(16);
				if (i==1 | i==2) {
					Image image1 = (i==1)?portalB:portalO;
					Image image2 = (i==2)?portalB:portalO;
					
					Portalforme p1 = new Portalforme(50,container.getHeight()/2-500*totalCompt-(r.nextInt(container.getHeight()/2)-container.getHeight()/4),p,image1);
					Portalforme p2 = new Portalforme(container.getWidth()-190,container.getHeight()/2-500*totalCompt-(r.nextInt(container.getHeight()/2)-container.getHeight()/4),p,image2);
					p1.setCouple(p2);
					p2.setCouple(p1);
					world.addPlateforme(p1);
					world.addPlateforme(p2);
				} else {
					world.addPlateforme(new PlateformeClassique(r.nextInt(2)*(container.getWidth()-130)+50,container.getHeight()/2-500*totalCompt,200,30,horizontale,p,image));
				}
			} else {
				totalCompt++;
				world.addPlateforme(new PlateformeMouvante(r.nextInt(2)*(container.getWidth()-130)+50,container.getHeight()/2-500*totalCompt,200,30,horizontale,p,image));
			}
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (500*totalCompt<p.getScore()+1200) {
			totalCompt+=1;
			if (compt==0) {
				createPlateforme(container, r.nextInt(4)>0, true);	
				compt = r.nextInt(6);
			} else {
				createPlateforme(container, r.nextInt(4)>0, false);
				compt--;
			}
		}
	}
	
}
