package com.feelfree.ctb

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport

class CatchTheBlocks : ApplicationAdapter() {
    lateinit var renderer: ShapeRenderer
    lateinit var camera : OrthographicCamera
    lateinit var viewport : FitViewport
    lateinit var debugRenderer : Box2DDebugRenderer
    lateinit var blockBody : Body
    var world = World(Vector2(0f, -10f), true)
    val BOX2D_SCALE = 30f
    val viewportX = 500f / BOX2D_SCALE
    val viewportY = 900f / BOX2D_SCALE

    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, viewportX, viewportY)
        viewport = FitViewport (viewportX, viewportY, camera)
        renderer = ShapeRenderer()
        viewport.apply()

        debugRenderer = Box2DDebugRenderer()
        // Create bodies
        var block = BodyDef()
        block.type = BodyDef.BodyType.DynamicBody
        block.position.set(viewportX/3 + viewportX/6, viewportY + viewportY/20)

        blockBody = world.createBody(block)
        blockBody.gravityScale = 2f
        var Pshape = PolygonShape()
        Pshape.setAsBox(viewportX/6, viewportY/20)

        var fixdef = FixtureDef()
        fixdef.run {
            shape = Pshape
            density = 5f
            friction = 0f
        }

        blockBody.createFixture(fixdef)
    }

    override fun render() {
        camera.update()
        val backgroundColor = Color.GREEN
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.run {
            world.step(1/60f, 6, 2)
            debugRenderer.render(world, camera.combined)
            projectionMatrix = camera.combined
            begin(ShapeRenderer.ShapeType.Filled)
            color = backgroundColor
            rect(0f, 0f, viewportX, viewportY)
            color = Color.GRAY
            rect(viewportX/3, 0f, viewportX/3, viewportY)
            color = Color.RED
            rect(blockBody.position.x - viewportX/6, blockBody.position.y - viewportY/20, viewportX/3, viewportY/10)
            end()
        }

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        renderer.dispose()
    }
}