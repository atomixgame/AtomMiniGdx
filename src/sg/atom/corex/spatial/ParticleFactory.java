/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.spatial;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 *
 * @author cuong.nguyen
 */
public class ParticleFactory {

    private AssetManager assetManager;

    public ParticleFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public ParticleEmitter createFire() {
        ParticleEmitter fireEffect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/flame.png"));
        fireEffect.setMaterial(fireMat);
        fireEffect.setImagesX(2);
        fireEffect.setImagesY(2); // 2x2 texture animation
        fireEffect.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
        fireEffect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        //fireEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        fireEffect.setStartSize(0.6f);
        fireEffect.setEndSize(0.1f);
        fireEffect.setGravity(0f, 0f, 0f);
        fireEffect.setLowLife(0.5f);
        fireEffect.setHighLife(3f);
        fireEffect.getParticleInfluencer().setVelocityVariation(0.3f);

        return fireEffect;

    }

    public ParticleEmitter createDerbis() {
        ParticleEmitter debrisEffect = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/Debris.png"));
        debrisEffect.setMaterial(debrisMat);
        debrisEffect.setImagesX(3);
        debrisEffect.setImagesY(3); // 3x3 texture animation
        debrisEffect.setRotateSpeed(4);
        debrisEffect.setSelectRandomImage(true);
        debrisEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        debrisEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
        debrisEffect.setGravity(0f, 6f, 0f);
        debrisEffect.getParticleInfluencer().setVelocityVariation(.60f);

        return debrisEffect;
    }
//    effectsModel = (Node) assetManager.loadModel("Models/FX/Effects.j3o");
//        ParticleEmitter fireEffect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
//        Material fireMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
//        fireMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/flame.png"));
//        fireEffect.setMaterial(fireMat);
//        fireEffect.setImagesX(2);
//        fireEffect.setImagesY(2); // 2x2 texture animation
//        fireEffect.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
//        fireEffect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
//        //fireEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
//        fireEffect.setStartSize(0.6f);
//        fireEffect.setEndSize(0.1f);
//        fireEffect.setGravity(0f, 0f, 0f);
//        fireEffect.setLowLife(0.5f);
//        fireEffect.setHighLife(3f);
//        fireEffect.getParticleInfluencer().setVelocityVariation(0.3f);
//        //worldNode.attachChild(fireEffect);
//
//        /**
//         * Explosion effect. Uses Texture from jme3-test-data library!
//         */
//        ParticleEmitter debrisEffect = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
//        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
//        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/Debris.png"));
//        debrisEffect.setMaterial(debrisMat);
//        debrisEffect.setImagesX(3);
//        debrisEffect.setImagesY(3); // 3x3 texture animation
//        debrisEffect.setRotateSpeed(4);
//        debrisEffect.setSelectRandomImage(true);
//        debrisEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
//        debrisEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
//        debrisEffect.setGravity(0f, 6f, 0f);
//        debrisEffect.getParticleInfluencer().setVelocityVariation(.60f);
//        //worldNode.attachChild(debrisEffect);
//        debrisEffect.emitAllParticles();
//
//        /**
//         * Illuminated bumpy rock with shiny effect. Uses Texture from
//         * jme3-test-data library! Needs light source!
//         */
//        Sphere ballMesh = new Sphere(32, 32, 0.1f);
//        Geometry ballGeo = new Geometry("RedBall", ballMesh);
//        Material unshadedMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        unshadedMat.setColor("Color", ColorRGBA.White); // needed for shininess
//        ballGeo.setMaterial(unshadedMat);
}
